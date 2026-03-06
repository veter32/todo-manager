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
package com.evsoft.service;

import com.evsoft.dto.TaskRequest;
import com.evsoft.mapper.TaskMapper;
import com.evsoft.model.Task;
import com.evsoft.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Task management.
 * Encapsulates business logic and interacts with {@link TaskRepository}.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    /**
     * Retrieves all tasks from the repository.
     *
     * @return List of task entities.
     */
    public List<Task> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a single task by its ID.
     *
     * @param id Unique identifier of the task.
     * @return Optional containing the found task, or empty if not found.
     */
    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Creates and persists a new task entity.
     *
     * @param request Data transfer object containing task details.
     * @return The saved task entity.
     */
    @Transactional
    public Task add(TaskRequest request) {
        Task task = mapper.toEntity(request);
        task.setId(null);
        return repository.save(task);
    }

    /**
     * Updates an existing task's state.
     * This method uses JPA dirty checking to persist changes.
     *
     * @param id      ID of the task to update.
     * @param request Updated data.
     * @return The updated task entity.
     * @throws EntityNotFoundException if task with given ID does not exist.
     */
    @Transactional
    public Task update(Long id, TaskRequest request) {
        Task existingTask = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        mapper.updateEntityFromDto(request, existingTask);

        return existingTask;
    }

    /**
     * Removes a task from the database.
     *
     * @param id ID of the task to delete.
     * @throws EntityNotFoundException if task with given ID does not exist.
     */
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        repository.deleteById(id);
    }
}