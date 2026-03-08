# Security Specification

This project implements an authentication and authorization system to protect personal data and manage API access.

---

## Authentication Mechanism

The API uses the **HTTP Basic Authentication** protocol. This means that every protected request must include credentials in the Authorization header.

* **Type:** Basic Auth
* **Header Format:** Authorization: Basic <base64(username:password)>
* **Password Hashing:** All passwords in the system are stored encrypted using the **BCrypt** hashing algorithm.

---

## Access Rules

| Endpoint | Method | Access | Description |
| :--- | :--- | :--- | :--- |
| /api/v1/tasks/** | ANY | ROLE_ADMIN | Core task management API. |
| /api/v1/swagger-ui/** | GET | PermitAll | Documentation web interface. |
| /v3/api-docs/** | GET | PermitAll | OpenAPI specification (JSON/YAML). |

> Note: Documentation resources (Swagger) are open without authentication to facilitate integration; however, executing requests from the UI requires entering credentials via the Authorize button.

---

## Testing and Development

A static administrator account has been created for the local environment:

* Username: admin
* Password: password

### CLI Request Example
`curl -X GET http://localhost:8080/api/v1/tasks -u admin:password`

### Testing with MockMvc (JUnit)
Tests use the `@WithMockUser(roles = "ADMIN")` annotation to simulate a security context without actually passing HTTP headers. This allows for isolated business logic testing.