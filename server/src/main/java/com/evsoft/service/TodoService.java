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
package com.evsoft.service;

import com.evsoft.model.Todo;
import com.evsoft.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing TO-DO entities.
 * Provides basic CRUD operations.
 */
@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns a list of all TO-DO items.
     *
     * @return List of all TODOs.
     */
    public List<Todo> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a TODO item by its id.
     *
     * @param id The id of the TODO.
     * @return Optional containing the TODO if found, or empty if not.
     */
    public Optional<Todo> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Adds a new TODO item.
     *
     * @param todo The TODO to add.
     * @return The saved TODO with generated id.
     */
    public Todo add(Todo todo) {
        todo.validate();
        todo.setId(null); // Ensure id is null so it will be auto-generated.
        return repository.save(todo);
    }

    /**
     * Updates an existing TODO item by id.
     *
     * @param id The id of the TODO to update.
     * @param updated The TODO data to update.
     * @return The updated TODO.
     * @throws RuntimeException if the TODO with the given id does not exist.
     */
    public Todo update(Long id, Todo updated) {
        updated.validate();
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setCompleted(updated.isCompleted());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));
    }

    /**
     * Deletes a TODO item by id.
     *
     * @param id The id of the TODO to delete.
     * @throws RuntimeException if the TODO with the given id does not exist.
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Todo not found with id " + id);
        }
        repository.deleteById(id);
    }
}
