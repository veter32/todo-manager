## Environment and Profile Management

This project uses Spring Profiles to manage different configurations for development and production environments. This ensures data safety and provides the right tools at the right stage of development.

### Available Profiles

* **dev (Default)**: H2 In-Memory, data is wiped on restart, H2 Console is enabled.
* **prod**: H2 File-based, data is stored in ./db/, H2 Console is disabled.

---

### Configuration Details

#### 1. Global Settings (application.properties)
* API Prefix: /api/v1 (e.g., http://localhost:8080/api/v1/tasks)
* Default Profile: dev

#### 2. Development Profile (application-dev.properties)
* JDBC URL: jdbc:h2:mem:taskdb
* DDL Strategy: create-drop
* Swagger UI: Enabled at /api/v1/swagger-ui.html

#### 3. Production Profile (application-prod.properties)
* JDBC URL: jdbc:h2:file:./db/task_manager;AUTO_SERVER=TRUE
* DDL Strategy: update
* Security: H2 Web Console and SQL logging are disabled.

---

### How to Switch Profiles

#### Via IntelliJ IDEA
1. Go to **Run/Debug Configurations**.
2. Select your Application.
3. In the **Active Profiles** field, enter: prod

#### Via Command Line (Gradle)
```
./gradlew bootRun --args='--spring.profiles.active=prod'
```
---

### Docker Integration

When running in a container, use Volumes to ensure the database file survives.

**Command:**
```
docker run -d --name task-manager -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod -v $(pwd)/db:/app/db your-image-name
```
