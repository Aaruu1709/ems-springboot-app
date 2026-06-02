# Employee Management System

A simple Employee Management System built using Spring Boot, Spring Data JPA, Hibernate, and MySQL.

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman
- Git & GitHub

---

## Project Architecture

```text
Postman
   ↓
Controller
   ↓
Service Interface
   ↓
Service Implementation
   ↓
Repository
   ↓
Hibernate/JPA
   ↓
MySQL Database
```

---

## Progress Update

### Day 1: Project Setup

- Created Spring Boot Project
- Added Required Dependencies
- Configured Application Properties
- Connected Spring Boot with MySQL
- Verified Database Connection

---

### Day 2 & 3 : CRUD Operations Completed

#### Entity Layer

- Created Employee Entity
- Mapped Entity with Database Table
- Configured Primary Key using @Id
- Used @GeneratedValue for Auto Increment

#### Repository Layer

- Created EmployeeRepository
- Extended JpaRepository
- Learned how Spring Data JPA provides built-in CRUD methods

#### Service Layer

- Created EmployeeService Interface
- Created EmployeeServiceImpl Class
- Implemented Constructor Injection
- Implemented Business Logic Layer

#### Controller Layer

- Created EmployeeController
- Added REST API Endpoints
- Used @RequestBody
- Used @PathVariable

#### CRUD APIs Implemented

- Create Employee
- Get All Employees
- Get Employee By ID
- Update Employee
- Delete Employee

#### Day 4: Exception Handling

- Created custom exception: EmployeeNotFoundException
- Used orElseThrow() instead of orElse(null)
- Implemented GlobalExceptionHandler using @RestControllerAdvice
- Handled EmployeeNotFoundException centrally using @ExceptionHandler
- Returned meaningful error messages when employee records were not found
- Improved API design by avoiding null responses
---

## API Endpoints

### Create Employee

```http
POST /employees
```

### Get All Employees

```http
GET /employees/allEmployees
```

### Get Employee By ID

```http
GET /employees/{id}
```

### Update Employee

```http
PUT /employees/{id}
```

### Delete Employee

```http
DELETE /employees/{id}
```

---

## Key Concepts Learned

### Spring Boot

- Spring Boot helps build production-ready applications quickly.
- Auto Configuration reduces manual configuration.
- Starter Dependencies simplify dependency management.

### Entity

- Entity class represents a database table.
- Each object represents one row in the table.

### Repository

- Repository layer communicates with the database.
- JpaRepository provides built-in CRUD methods.
- Common methods:
  - save()
  - findAll()
  - findById()
  - deleteById()

### Service Layer

- Service layer contains business logic.
- Keeps controller clean and maintainable.
- Helps separate responsibilities.

### Constructor Injection

- Preferred over field injection.
- Makes dependencies explicit.
- Improves testability.
- Promotes loose coupling.

### Optional

- findById() returns Optional.
- Helps avoid NullPointerException.
- Provides safer handling of missing records.

### REST APIs

#### GET

Used to fetch data.

#### POST

Used to create data.

#### PUT

Used to update existing data.

#### DELETE

Used to delete data.

### @RequestBody

- Reads JSON data from request body.
- Converts JSON into Java Object.

### @PathVariable

- Reads dynamic values from URL.
- Used for operations like getById, update, and delete.

---

## Challenges Faced and Solutions

### Challenge 1: Data Not Saving in Database

Issue:

- Request body was missing.

Solution:

- Added JSON data in Postman request body.
- Used @RequestBody annotation.

---

### Challenge 2: 400 Bad Request

Issue:

- PUT request was sent without request body data.

Solution:

- Sent proper JSON payload along with PUT request.

---

### Challenge 3: Confusion Between int and Integer

Learning:

- int cannot store null values.
- Integer can store null values.
- Wrapper classes provide additional flexibility.

---

### Challenge 4: Understanding save() Method

Learning:

- save() performs insert when record does not exist.
- save() performs update when record already exists.

---

### Challenge 5: Understanding Update Flow

Learning:

Before updating:

1. Find record using findById()
2. Check if record exists
3. Update required fields
4. Save updated entity

---

### Challenge 6: Understanding Layered Architecture

Learning:

```text
Controller → Handles Requests

Service → Contains Business Logic

Repository → Database Operations

Database → Stores Data
```

---

## Interview Notes

### Why Service Layer?

To keep business logic separate from controller logic and improve maintainability.

### Why Repository Layer?

To interact with database using Spring Data JPA.

### Why Constructor Injection?

- Preferred in modern Spring Boot applications.
- Improves testability.
- Makes dependencies explicit.

### Why Optional?

To safely handle missing records and avoid NullPointerException.

### Difference Between @RequestBody and @PathVariable?

@RequestBody:

- Reads data from request body.

@PathVariable:

- Reads data from URL path.

### Why save() Works for Both Insert and Update?

Because JpaRepository checks whether the entity already exists.

- New record → Insert
- Existing record → Update

---

## Future Enhancements

- Global Exception Handling
- ResponseEntity
- Validation using @Valid
- DTO Pattern
- Lombok
- Pagination
- Sorting
- Swagger/OpenAPI Documentation
- Unit Testing using JUnit
- Spring Security with JWT Authentication

---

## Author

Arti Shinde

This project is being developed as part of my Spring Boot learning journey to strengthen backend development skills and prepare for software engineering interviews.

---

## Developer Journal

### Biggest Realizations

- Repository layer does not need manual CRUD implementation because JpaRepository provides built-in methods.
- save() is used for both insert and update operations.
- Constructor Injection is preferred over field injection.
- Service layer helps keep business logic separate from controller logic.
- REST APIs require both URL parameters and request body data depending on the operation.

### Mistakes I Made

- Sent PUT request without request body.
- Confused int and Integer handling.
- Expected Postman to auto-populate data during update operations.

### What I Learned From Those Mistakes

- Always verify request payloads.
- Understand request flow before debugging.
- Read error messages carefully before changing code.

- ------------------------------------------------------------------------------

## Personal Note

This project helped me understand:

- Spring Boot Fundamentals
- Layered Architecture
- CRUD Operations
- REST API Development
- MySQL Integration
- JPA and Hibernate Concepts
- Real-world Backend Development Flow

The goal is not only to build the project but also to understand the reasoning behind every layer and every piece of code.
