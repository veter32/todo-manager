/*
 * Personal Task Manager
 * Copyright (c) ${YEAR} Vitalii Yeremenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 */

package com.evsoft.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn401WhenAccessingTasksWithoutAuth() throws Exception {
        mockMvc.perform(get("/tasks"))
               .andExpect(status().isUnauthorized()); // Ждем 401
    }

    @Test
    void shouldReturn401WithWrongCredentials() throws Exception {
        mockMvc.perform(get("/tasks")
                        .with(httpBasic("admin", "wrong-password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn200WithValidCredentials() throws Exception {
        mockMvc.perform(get("/tasks")
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAllowAccessToSwaggerWithoutAuth() throws Exception {
        // Проверяем, что наши исключения в SecurityConfig работают
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }
}