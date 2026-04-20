# University Workshop Registration System
## Backend Report — Midterm Project (Part 1 of Final Project)

---

## Title Page

**Project Title:** University Workshop Registration System — Backend  
**Course:** Software Engineering / Web Development  
**Submission Type:** Midterm Project (Part 1 of Final Project)  
**Technology Stack:** Java 17+, Spring Boot 3.3.5, Spring Data JPA, MySQL, Maven  

---

## 1. Project Overview

The University Workshop Registration System is a RESTful backend API built with Spring Boot. It allows university staff and students to manage users, workshops, and registrations. The system enforces business rules such as preventing duplicate registrations and enforcing workshop capacity limits.

The backend follows a layered architecture:
- **Entity Layer** — JPA-mapped database tables
- **Repository Layer** — Data access via Spring Data JPA
- **Service Layer** — All business logic
- **Controller Layer** — REST API endpoints

---

## 2. Database Design

### Tables

#### `users`
| Column | Type         | Constraints          |
|--------|--------------|----------------------|
| id     | BIGINT       | PK, AUTO_INCREMENT   |
| name   | VARCHAR(255) | NOT NULL             |
| email  | VARCHAR(255) | NOT NULL, UNIQUE     |

#### `workshops`
| Column      | Type         | Constraints        |
|-------------|--------------|--------------------|
| id          | BIGINT       | PK, AUTO_INCREMENT |
| title       | VARCHAR(255) | NOT NULL           |
| description | VARCHAR(255) | NOT NULL           |
| location    | VARCHAR(255) | NOT NULL           |
| date        | DATE         | NOT NULL           |
| capacity    | INT          | NOT NULL           |

#### `registrations`
| Column            | Type     | Constraints                        |
|-------------------|----------|------------------------------------|
| id                | BIGINT   | PK, AUTO_INCREMENT                 |
| user_id           | BIGINT   | FK → users(id), NOT NULL           |
| workshop_id       | BIGINT   | FK → workshops(id), NOT NULL       |
| registration_date | DATETIME | NOT NULL                           |
|                   |          | UNIQUE(user_id, workshop_id)       |

---

## 3. Entity Relationship Explanation

```
User (1) ────────── (Many) Registration (Many) ────────── (1) Workshop
```

- A **User** can have many **Registrations**
- A **Workshop** can have many **Registrations**
- A **Registration** belongs to exactly one **User** and one **Workshop**
- The `registrations` table has a **composite unique constraint** on `(user_id, workshop_id)` to enforce the no-duplicate-registration rule at the database level

### JPA Relationships
- `User` → `Registration`: `@OneToMany(mappedBy = "user")`
- `Workshop` → `Registration`: `@OneToMany(mappedBy = "workshop")`
- `Registration` → `User`: `@ManyToOne @JoinColumn(name = "user_id")`
- `Registration` → `Workshop`: `@ManyToOne @JoinColumn(name = "workshop_id")`

---

## 4. Business Rules Implementation Summary

All business rules are implemented exclusively in the **Service Layer**.

### Rule 1 — No Duplicate Registrations
**Location:** `RegistrationService.registerUser()`  
**Logic:** Before saving, the service calls `registrationRepository.existsByUserIdAndWorkshopId(userId, workshopId)`. If it returns `true`, a `DuplicateRegistrationException` is thrown, which the global exception handler maps to HTTP **409 Conflict**.

### Rule 2 — Workshop Capacity Enforcement
**Location:** `RegistrationService.registerUser()`  
**Logic:** The service counts existing registrations for the workshop using `registrationRepository.countByWorkshopId(workshopId)`. If the count is >= the workshop's capacity, a `WorkshopFullException` is thrown, mapped to HTTP **409 Conflict**.

### Rule 3 — Input Validation
**Location:** All Request DTOs (`UserRequest`, `WorkshopRequest`, `RegistrationRequest`)  
**Logic:** Jakarta Validation annotations (`@NotBlank`, `@NotNull`, `@Email`, `@Min`, `@FutureOrPresent`) ensure data integrity before it reaches the service layer. Violations return HTTP **400 Bad Request**.

---

## 5. REST API Design Summary

### Users

| Method | Endpoint          | Description         | Status Code |
|--------|-------------------|---------------------|-------------|
| POST   | /api/users        | Create a new user   | 201 Created |
| GET    | /api/users        | Get all users       | 200 OK      |
| GET    | /api/users/{id}   | Get user by ID      | 200 OK      |

### Workshops

| Method | Endpoint             | Description            | Status Code |
|--------|----------------------|------------------------|-------------|
| POST   | /api/workshops       | Create a new workshop  | 201 Created |
| GET    | /api/workshops       | Get all workshops      | 200 OK      |
| GET    | /api/workshops/{id}  | Get workshop by ID     | 200 OK      |

### Registrations

| Method | Endpoint              | Description                        | Status Code  |
|--------|-----------------------|------------------------------------|--------------|
| POST   | /api/registrations    | Register a user for a workshop     | 201 Created  |
| GET    | /api/registrations    | Get all registrations              | 200 OK       |

### Error Responses

| Scenario                        | Status Code  |
|---------------------------------|--------------|
| Resource not found              | 404 Not Found |
| Duplicate registration          | 409 Conflict  |
| Workshop full                   | 409 Conflict  |
| Invalid input data              | 400 Bad Request |

---

## 6. Sample Request / Response Examples

### POST /api/users
**Request:**
```json
{
  "name": "John Doe",
  "email": "john@test.com"
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@test.com"
}
```

---

### POST /api/workshops
**Request:**
```json
{
  "title": "Spring Boot 101",
  "description": "Intro to Spring",
  "location": "Room A1",
  "date": "2026-04-01",
  "capacity": 30
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Spring Boot 101",
  "description": "Intro to Spring",
  "location": "Room A1",
  "date": "2026-04-01",
  "capacity": 30
}
```

---

### POST /api/registrations
**Request:**
```json
{
  "userId": 1,
  "workshopId": 1
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "userId": 1,
  "userName": "John Doe",
  "workshopId": 1,
  "workshopTitle": "Spring Boot 101",
  "registrationDate": "2026-03-20T01:10:41.557"
}
```

---

### POST /api/registrations (Duplicate — Error)
**Request:**
```json
{
  "userId": 1,
  "workshopId": 1
}
```
**Response (409 Conflict):**
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "User is already registered for this workshop"
}
```

---

## 7. Postman Testing Screenshots

> 📸 **Add your Postman screenshots here.**
>
> Suggested screenshots to include:
> 1. POST /api/users — success (201)
> 2. POST /api/workshops — success (201)
> 3. POST /api/registrations — success (201)
> 4. POST /api/registrations — duplicate (409 Conflict)
> 5. GET /api/users — list all users

---

## 8. Contribution Summary

| Student Name | Responsibilities                          | Features Implemented                                              |
|--------------|-------------------------------------------|-------------------------------------------------------------------|
| [Name 1]     | Phase 1 — Foundation                      | User entity, Workshop entity, Repositories, Services, Controllers |
| [Name 2]     | Phase 2 — Registration Engine             | Registration entity, RegistrationService, RegistrationController  |

> ✏️ Fill in your actual names and responsibilities above.

---

*End of Backend Report*

