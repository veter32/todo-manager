/*
 * Personal Task Manager
 * Copyright (c) 2026 Vitalii Yeremenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 */
package com.evsoft.api;

import com.evsoft.dto.TaskRequest;
import com.evsoft.dto.TaskResponse;
import com.evsoft.mapper.TaskMapper;
import com.evsoft.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Task resources.
 * Handles HTTP requests and delegates business logic to {@link TaskService}.
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "API for task management operations")
public class TaskController {

    private final TaskService service;
    private final TaskMapper mapper;

    /**
     * Retrieves a list of all existing tasks.
     *
     * @return List of {@link TaskResponse} objects.
     */
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Returns a complete list of tasks stored in the database.")
    public List<TaskResponse> getAll() {
        return service.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Retrieves a specific task by its unique identifier.
     *
     * @param id The ID of the task to retrieve.
     * @return The {@link TaskResponse} if found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Returns a single task identified by its ID.")
    public TaskResponse getById(@PathVariable Long id) {
        return service.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    /**
     * Creates a new task based on the provided request data.
     *
     * @param request The task data transfer object containing title and status.
     * @return The created {@link TaskResponse} with a generated ID.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new task")
    public TaskResponse add(@Valid @RequestBody TaskRequest request) {
        return mapper.toResponse(service.add(request));
    }

    /**
     * Updates an existing task with new data.
     *
     * @param id      The ID of the task to update.
     * @param request The new data for the task.
     * @return The updated {@link TaskResponse}.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return mapper.toResponse(service.update(id, request));
    }

    /**
     * Deletes a task from the system.
     *
     * @param id The ID of the task to be removed.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a task")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}