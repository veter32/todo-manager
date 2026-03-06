# Task Manager Server

The backend core of the Personal Task Manager, built with **Java 21** and **Spring Boot 3.4**. This module implements a robust RESTful API with a focus on clean architecture, data validation, and automated testing.

---

## Architecture Overview

This server follows a classic **3-layer architecture** to ensure a strict separation of concerns:

1.  **Web Layer (`TaskController`)**: Handles HTTP requests, manages API versioning, and performs DTO-to-Entity conversion.
2.  **Service Layer (`TaskService`)**: Encapsulates business logic, manages transactions (`@Transactional`), and ensures data consistency.
3.  **Data Layer (`TaskRepository`)**: Interacts with the database using Spring Data JPA and Hibernate.

---

## Tech Stack

* **Language**: Java 21 (LTS)
* **Framework**: Spring Boot 3.4
* **Database**: H2 (In-memory for development) / PostgreSQL (Production ready)
* **Mapping**: MapStruct (High-performance DTO mapping)
* **Validation**: Jakarta Bean Validation (Hibernate Validator)
* **Documentation**: SpringDoc OpenAPI (Swagger UI)
* **Testing**: JUnit 5, MockMvc, AssertJ

---

## Getting Started

### Prerequisites
* **JDK 17** or higher
* **Gradle 8.x** (wrapper included)

### Run the Application
From the `server` directory, run:

Linux, Mac: `./gradlew bootRun`

Windows `gradlew bootRun`

## 📚 Documentation & Guides

Detailed technical guides are available in the `docs/` directory:

* [** REST API Specification**](./docs/api.md) — Full list of endpoints, request/response JSON examples, and status codes.
* [** Docker Setup Guide**](./docs/docker.md) — Instructions on how to containerize the server and run it with PostgreSQL.
