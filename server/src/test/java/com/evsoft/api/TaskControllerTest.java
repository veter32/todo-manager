/*
 *
 *  * Personal Task Manager
 *  * Copyright (c) ${YEAR} Vitalii Yeremenko
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 */
package com.evsoft.api;

import com.evsoft.dto.TaskRequest;
import com.evsoft.model.Task;
import com.evsoft.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private ObjectMapper objectMapper; // Helper to convert objects to JSON

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoTasks() throws Exception {
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldAddNewTaskSuccessfully() throws Exception {
        // Create Request DTO instead of raw String
        TaskRequest request = new TaskRequest("Test Task", false);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.completed").value(false));

        // Check DB state
        assertEquals(1, repository.count());
    }

    @Test
    void shouldUpdateExistingTask() throws Exception {
        Task existing = repository.save(new Task(null, "Initial Title", false));
        TaskRequest updateRequest = new TaskRequest("Updated Title", true);

        mockMvc.perform(put("/api/v1/tasks/" + existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldDeleteTaskSuccessfully() throws Exception {
        Task task = repository.save(new Task(null, "Delete Me", false));

        mockMvc.perform(delete("/api/v1/tasks/" + task.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, repository.count());
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentTask() throws Exception {
        TaskRequest request = new TaskRequest("Title", true);

        mockMvc.perform(put("/api/v1/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound()); // This will work if you have GlobalExceptionHandler
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {
        TaskRequest invalidRequest = new TaskRequest("", false);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        assertEquals(0, repository.count());
    }
}
