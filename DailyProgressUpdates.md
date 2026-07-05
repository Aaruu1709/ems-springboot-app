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

### Day 11-12-13 ###
---
# Day Progress — Spring Security + JWT Authentication Foundation 🔐

## Objective

Convert Employee Management System from open APIs into secured industry-style APIs using Spring Security and JWT.

---

## Features Implemented

### 1. Spring Security Configuration

Created SecurityConfig to control API access.

Configured:

* Swagger endpoints → public
* Authentication endpoints → public
* Remaining APIs → protected

Concept learned:

* SecurityFilterChain
* HttpSecurity
* requestMatchers()
* permitAll()
* authenticated()

---

### 2. Authentication Module

Created authentication layer.

Created:

* auth package
* LoginRequest DTO
* AuthController

Implemented:
POST /auth/login

Concept learned:
Authentication = Verify WHO user is

---

### 3. Dummy Login Validation

Implemented temporary credential validation.

Example:
username = jack
password = 123

Valid credentials →
LOGIN SUCCESS

Invalid credentials →
INVALID CREDENTIALS

---

### 4. JWT Integration

Added JWT dependencies.

Created:

* jwt package
* JwtService

Implemented:

* generateToken()
* token expiry
* subject(username)
* signing

Output:
JWT token generated successfully.

Concept learned:
JWT = JSON Web Token

Structure:
Header.Payload.Signature

---

### 5. JWT Filter Creation

Created JwtFilter using OncePerRequestFilter.

Implemented:

* Read Authorization header
* Receive JWT
* Extract username

Console Output:
USER -> jack

Concept learned:
Request → Filter → Security → Controller

---

## Interview Concepts Covered

Authentication
→ Verify identity (WHO)

Authorization
→ Verify permission (WHAT)

JWT
→ Stateless authentication

Security Filter Chain
→ Request validation pipeline

---

## Final Achievement

Login

↓

Generate JWT

↓

Send Token

↓

Read Token

↓

Extract User

↓

Ready for Authorization




---
### day 14-15###

# Module 15: Redis Caching

## What is Redis?

Redis is an in-memory database used to store frequently accessed data in RAM, so that data can be fetched faster without hitting the main database every time.

---

## Why do we use Redis?

* Improve application performance
* Reduce database load
* Return responses faster
* Store frequently used data in memory

---

## Redis Setup

We used **Memurai** (Redis for Windows) because our system is Windows 11.

Commands used:

```bash
cd "C:\Program Files\Memurai"

memurai-cli.exe

ping
```

Output:

```bash
PONG
```

This means Redis is running successfully.

---

## Enable Caching in Spring Boot

```java
@EnableCaching
```

This annotation enables caching support in the application.

---

## @Cacheable

```java
@Cacheable(
    value = "employees",
    key = "#id"
)
```

### Purpose

Stores method results in Redis.

### Flow

```text
First Request

Client
 ↓
Database
 ↓
Redis Cache

Second Request

Client
 ↓
Redis Cache
(No Database Call)
```

### Interview Answer

> @Cacheable is used to store method results in Redis. If the same request comes again, Spring returns data from cache instead of calling the database.

---

## Serializable DTO

Redis stores Java objects using serialization.

So DTO classes must implement Serializable.

```java
public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = 1L;

}
```

### Interview Answer

> We implement Serializable because Redis converts Java objects into bytes before storing them in memory.

---

## Checking Data in Redis

Command:

```bash
keys *
```

Example Output:

```bash
employees::22
employees::23
```

This confirms that employee data is stored in Redis.

---

## Problem: Stale Cache

Example:

```text
Database:

Employee 23 -> Deleted

Redis:

employees::23 -> Still Exists
```

This is called Stale Cache.

---

## @CacheEvict

```java
@CacheEvict(
    value = "employees",
    key = "#id"
)
```

### Purpose

Removes old data from Redis when data is updated or deleted.

### Used In

* Update API
* Delete API

### Interview Answer

> @CacheEvict removes stale data from Redis whenever records are updated or deleted, ensuring users always get fresh data.

---

## @CachePut

```java
@CachePut(
    value = "employees",
    key = "#id"
)
```

### Purpose

Updates Redis immediately with the latest data after method execution.

### Interview Answer

> @CachePut updates the cache with new data after executing the method, while @CacheEvict removes old cache data.

---

## Cache Aside Pattern

### Flow

```text
GET Request

Check Redis
 ↓
Data Found → Return Data

OR

Data Not Found
 ↓
Fetch From Database
 ↓
Store In Redis
 ↓
Return Response
```

### Update/Delete Flow

```text
Update/Delete Database
 ↓
Remove Cache
 ↓
Next GET Creates Fresh Cache
```

### Interview Answer

> In Cache Aside Pattern, the application first checks Redis. If data is not available, it fetches data from the database and stores it in Redis. During updates or deletes, the cache is invalidated so fresh data is loaded later.

---

# Difference Between Cache Annotations

| Annotation  | Purpose                  | Used In          |
| ----------- | ------------------------ | ---------------- |
| @Cacheable  | Store data in Redis      | GET APIs         |
| @CacheEvict | Remove old cache         | PUT, DELETE APIs |
| @CachePut   | Update cache immediately | PUT APIs         |

---

# Most Important Redis Interview Questions

1. What is Redis?
2. Why do we use Redis?
3. Difference between Redis and MySQL?
4. What is In-Memory Database?
5. What is @Cacheable?
6. What is @CacheEvict?
7. What is @CachePut?
8. What is Cache Aside Pattern?
9. Why should DTO implement Serializable?
10. How do you verify that Redis caching is working?
11. What happens if Redis goes down?
12. When should we not use Redis?

---

# My Learning

Today I learned:

* Redis installation using Memurai
* Spring Boot Redis integration
* @EnableCaching
* @Cacheable
* DTO Serialization
* Redis CLI commands
* @CacheEvict
* @CachePut theory
* Cache Aside Pattern
* Industry interview concepts related to Redis

-------------------------------------------------------------------

### day 17-18



# Docker Basics & Spring Boot Dockerization

## What is Docker?

Docker is a containerization platform that packages an application along with all its dependencies so that it runs consistently in every environment.

## Why Do We Use Docker?

* Eliminates the "works on my machine" problem.
* Provides the same environment for development, testing, and production.
* Makes deployment easy and fast.
* Supports microservices architecture.
* Helps in scaling applications efficiently.

## Important Docker Concepts

### Docker Image

A Docker image is a read-only blueprint that contains application code, dependencies, runtime, and configurations.

### Docker Container

A container is a running instance of a Docker image.

### Image vs Container

* Image = Java Class
* Container = Java Object

## Docker Architecture

Docker Client
↓
Docker Engine
↓
Docker Images
↓
Docker Containers

## First Docker Command

Command:

```bash
docker run hello-world
```

Internal Flow:

1. Docker checks whether the image exists locally.
2. If not, it downloads the image from Docker Hub.
3. Docker creates a container from the image.
4. The application inside the container runs.
5. Output is displayed on the terminal.
6. The container stops automatically.

## Dockerfile

A Dockerfile is a text file that contains instructions to build a Docker image.

### Dockerfile Used in Employee Management Project

```dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/SpringBootWithJDBC-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]
```

## Dockerfile Commands

### FROM

Specifies the base image.

Example:

```dockerfile
FROM eclipse-temurin:21-jdk
```

### WORKDIR

Sets the working directory inside the container.

Example:

```dockerfile
WORKDIR /app
```

### COPY

Copies files from the local machine to the container.

Example:

```dockerfile
COPY target/SpringBootWithJDBC-0.0.1-SNAPSHOT.jar app.jar
```

### EXPOSE

Documents the port used by the application.

Example:

```dockerfile
EXPOSE 8081
```

### ENTRYPOINT

Defines the command that runs when the container starts.

Example:

```dockerfile
ENTRYPOINT ["java","-jar","app.jar"]
```

## Building Spring Boot JAR

```bash
mvnw.cmd clean package
```

This command:

* Cleans old build files.
* Compiles source code.
* Runs JUnit tests.
* Generates an executable JAR file.

## Building Docker Image

```bash
docker build -t employee-app .
```

This command:

* Reads the Dockerfile.
* Pulls the Java base image.
* Copies the Spring Boot JAR.
* Creates a Docker image named `employee-app`.

## Running Docker Container

```bash
docker run -p 8081:8081 employee-app
```

Port Mapping:

```text
Host Machine Port (8081)
↓
Docker Container Port (8081)
```

## Challenge Faced

### Problem

Spring Boot application failed to connect to MySQL after running inside Docker.

### Root Cause

Inside a Docker container, `localhost` refers to the container itself, not the host machine.

### Learning

For proper communication between Spring Boot, MySQL, and Redis containers, Docker Compose and Docker networking should be used.

## Key Takeaways

* Learned Docker fundamentals.
* Understood Images and Containers.
* Created the first Dockerfile.
* Generated Spring Boot executable JAR.
* Built the first Docker image.
* Learned about Docker networking concepts and localhost behavior inside containers.











-------------------------------------------------------------------------------------
###DAY 19-20

Q1. What is Docker?
Answer

Docker is an open-source containerization platform that packages an application along with all its dependencies, libraries, and runtime into lightweight containers. These containers ensure that the application behaves consistently across different environments such as development, testing, and production.

Q2. What is the difference between a Docker Image and a Docker Container?
Answer

A Docker Image is a read-only blueprint that contains the application, runtime, libraries, and dependencies required to run the application. A Docker Container is a running instance of that image. We can create multiple containers from a single image.

Example:

Dockerfile
      ↓
Docker Image
      ↓
Docker Container
Q3. Why do we use Docker in Spring Boot projects?
Answer

Docker helps eliminate environment-related issues by packaging the Spring Boot application together with its runtime and dependencies. This makes deployment easier, improves portability, and ensures consistent execution across developer machines, testing servers, and production environments.

Q4. Describe your Docker implementation in your project.
Answer

In my Employee Management System project, I created a Dockerfile using Eclipse Temurin JDK 21 as the base image. I copied the executable Spring Boot JAR into the container, exposed port 8080, and configured the entry point to run the application. I built the Docker image using docker build and started the application using docker run. During implementation, I faced a database connectivity issue because the application inside the container was trying to connect to localhost. I analyzed the problem using docker logs and learned that localhost inside a container refers to the container itself, not the host machine. I understood that for local development we can use host.docker.internal, while in production environments Docker Compose is used to connect multiple containers through service names.



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
