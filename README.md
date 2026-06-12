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

  ### Day 5 & 6 :
  # Spring Boot – DTO + Validation Learning Notes

## What we learned today

### 1. DTO (Data Transfer Object)

DTO is used to control what data goes outside from backend to client.

Before DTO:
We were returning complete Employee entity.

Example response:

{
"id":1,
"firstName":"Arti",
"email":"[abc@gmail.com](mailto:abc@gmail.com)",
"salary":50000,
"department":"IT"
}

Problem:
Client receives unnecessary data.

Solution:
Create EmployeeDTO and return only required fields.

Example:

{
"firstName":"Arti",
"email":"[abc@gmail.com](mailto:abc@gmail.com)"
}

Flow:

Client
↓
Controller
↓
Service
↓
Repository
↓
Entity
↓
DTO
↓
Response

Simple memory line:

DTO = Control what goes OUT

## 2. Validation

Validation is used to check request data before saving into database.

Problem:

User can send:

{
"firstName":"",
"email":"abc"
}

Without validation → invalid data gets saved.

Solution:

Add validation annotations.

Example:

@NotBlank → empty value not allowed

@Email → invalid email not allowed

Add:

@Valid

inside controller request body.

Flow:

Request
↓
Validation
↓
Controller
↓
Service
↓
Database

Simple memory line:

Validation = Control what comes IN

## 3. Validation Error Handling

When validation fails:

Spring throws:

MethodArgumentNotValidException

We catch it using:

@ExceptionHandler

inside:

GlobalExceptionHandler

Flow:

Request
↓
Validation Failed
↓
MethodArgumentNotValidException
↓
GlobalExceptionHandler
↓
Custom Response

## 4. Important Bug We Solved

Issue:

400 Bad Request

Error:

Cannot map null into type double

Reason:

Primitive type cannot store null.

Wrong:

private double salary;

Correct:

private Double salary;

Why?

double → primitive → cannot store null

Double → wrapper → can store null

Industry preference in Entity:

Integer

Double

Long

Boolean

## Final Learning

DTO → Controls Output

Validation → Controls Input

Wrapper Classes → Help handle null values safely

---

## Day Progress 7-8

### Completed 

* Integrated MapStruct into the project
* Configured MapStruct processor in Maven
* Created EmployeeMapper interface
* Generated mapper implementation automatically
* Refactored manual DTO conversion into MapStruct mapping
* Cleaned ServiceImpl structure

### Refactored APIs

* Get All Employees
* Get Employee By Id
* Pagination API
* Search Employee API
* Filter By Department API

### Improvements

* Removed repetitive DTO creation code
* Reduced boilerplate code
* Improved maintainability
* Followed industry-style layered architecture

### Learning Outcome

Learned how MapStruct helps convert Entity ↔ DTO automatically and keeps service classes clean and scalable.
-----------------------------------------------------------------

### Day 9-10
-----------------------------------------------
# 1. DTO + MapStruct 
What is DTO and why did you use MapStruct?

DTO is used to transfer only required data between layers instead of exposing entity directly. Initially I mapped data manually but later integrated MapStruct because it generates mapping code at compile time, reduces boilerplate, improves readability and gives better performance than reflection-based mapping."
Example:
```text id="cq6k3d"
Entity
↓

MapStruct

↓

DTO
```

---

# 2. Standard API Response Wrapper
Why use ApiResponse?

> "I created a common ApiResponse wrapper so every API returns consistent structure. It contains message, status and actual data. This makes frontend integration easier and improves maintainability."

Example:

```json
{
"message":"Success",
"status":200,
"data":{}
}
```

---

# 3. Global Exception Handling 
Why Global Exception?
> "Instead of writing try-catch in every controller, I centralized exception handling using @RestControllerAdvice and @ExceptionHandler. It keeps controller clean and standardizes error responses."

Example:

```json
{
"message":"Employee not found",
"status":404
}
```

---

# 4. Validation Handling 
How did you validate request?
I used Bean Validation annotations like @NotBlank and @Email on DTO and handled validation errors globally using MethodArgumentNotValidException.
Example:

```java
@NotBlank
@Email
```

---

# 5. Logging (SLF4J + Logback) 

Explain logging."Logging is used to monitor application flow and debug issues. I used SLF4J as logging abstraction and Logback as implementation. I added info logs in service layer to track business operations.

Example:

```java
logger.info()
logger.warn()
logger.error()
```

Flow:

```text id="v5b33d"
Application

↓

SLF4J

↓

Logback

↓

Console
```

---

# 6. JPA Auditing
What is auditing?
I implemented JPA auditing to automatically maintain createdAt and updatedAt fields using @CreatedDate and @LastModifiedDate.

Example:

```text
createdAt
updatedAt
```

---

# 7. Soft Delete 
Why Soft Delete?
Instead of permanently removing records, I implemented soft delete using a deleted flag. Deleted data remains in database for audit and recovery purposes but stays hidden from APIs.

Flow:

```text id="ebqwnj"
DELETE

↓

deleted=true
```

---

# 8. Restore API 
How did restore work?
I created restore functionality using custom update query and transaction management to recover soft deleted records.

Flow:

```text id="2rggpm"
deleted=1

↓

restore

↓

deleted=0
```

---

# 9. Response Utility 
Why utility?
 I created ResponseUtil to remove duplicate response creation and keep controllers clean.

---

### Day 11-12###


------------------------------------------------------------------------------------

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
