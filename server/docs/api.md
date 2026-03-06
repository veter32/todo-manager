# Task Manager REST API Specification

This document provides a detailed overview of the available endpoints for the Task Manager Server.

**Base URL**: `http://localhost:8080/api/v1/tasks`

---

## Quick Reference Table

| Method | Endpoint | Description | Success Code | Error Codes |
| :--- | :--- | :--- | :--- | :--- |
| GET | / | List all tasks | 200 OK | 500 |
| GET | /{id} | Get task by ID | 200 OK | 404 |
| POST | / | Create a new task | 201 Created | 400 |
| PUT | /{id} | Update existing task | 200 OK | 400, 404 |
| DELETE | /{id} | Delete a task | 204 No Content | 404 |

---

## 🛠 Endpoint Details

### 1. Get All Tasks
Retrieves a list of all tasks.
* **URL**: GET /api/v1/tasks
* **Response Body**:
```json 
[
  {
    "id": 1,
    "title": "Learn Spring Boot",
    "completed": true
  }
]
```

---

### 2. Get Task by ID
* **URL**: GET /api/v1/tasks/{id}
* **Response Body**: Returns a single TaskResponse object.
```json
{
  "title": "Buy groceries",
  "completed": false
}
``` 
* **Error**: Returns 404 Not Found if the ID does not exist.

---

### 3. Create a New Task
* **URL**: POST /api/v1/tasks
* **Constraints**: Title must be 3-100 characters, not blank. 
* **Request Body (TaskRequest)**:
```json
{
  "title": "Buy groceries",
  "completed": false
}
```
---

### 4. Update a Task
* **URL**: PUT /api/v1/tasks/{id}
* **Request Body**: Same as Create.
* **Success**: 200 OK.
* **Error**: 404 if not found, 400 if validation fails.

---

### 5. Delete a Task
* **URL**: DELETE /api/v1/tasks/{id}
* **Success**: 204 No Content.

---

## ⚠️ Error Responses

### Validation Error (400 Bad Request)
```json
{
"title": "Title is required",
"status": 400
}
```

### Not Found (404 Not Found)
```json
{
"error": "Task not found with id: 99",
"status": 404
}
```

---
Copyright (c) 2026 Vitalii Yeremenko