# Project Challenges & Debugging

## 1. Swagger was blocked after adding Spring Security

Issue:
After integrating Spring Security, Swagger UI stopped opening and returned:

403 Forbidden
/v3/api-docs

Root Cause:
Security rules protected Swagger endpoints.

How I solved:
Updated SecurityConfig and allowed Swagger endpoints using requestMatchers() and permitAll().

Code Idea:
requestMatchers(
"/swagger-ui/**",
"/v3/api-docs/**"
).permitAll()

Learning:
When securing APIs, developer tools like Swagger must remain publicly accessible.

---

## 2. Soft Delete failed because of incorrect Hibernate filter

Issue:
Deleted employees were not filtered correctly.

Error:
Unknown column 'value' in where clause

Root Cause:
Wrong @Where configuration.

How I solved:
Added proper entity mapping and verified deleted column values.

Code Idea:
@SQLDelete(...)
@Where(clause="deleted=false")

Learning:
Entity filters should match actual DB schema.

---

## 3. JWT token was generated but authorization failed

Issue:
Employee APIs returned:

403 Forbidden

Root Cause:
JWT existed but request header was missing Bearer prefix.

Wrong:
Authorization:
eyJ...

Correct:
Authorization:
Bearer eyJ...

How I solved:
Validated Authorization header and fixed request format.

Learning:
Token generation and token transmission are different concerns.

---

## 4. JWT Filter executed but username extraction never happened

Issue:
Console showed:

TOKEN -> eyJ...

but user extraction failed.

Root Cause:
Condition checked:

startsWith("Bearer ")

Header format was incorrect.

How I solved:
Added proper Authorization header and verified request flow through filter.

Learning:
Always debug request headers before changing business logic.

---

## 5. Employee API still blocked after authentication

Issue:
Login generated token successfully but APIs remained inaccessible.

Root Cause:
Authentication existed but SecurityContext was not yet configured.

How I solved:
Debugged Security filter flow and confirmed token reached filter.

Learning:
Authentication and authorization are separate stages.

---

## Debugging Approach

Read Logs

↓

Identify Layer

↓

Validate Request

↓

Check Configuration

↓

Apply Fix

↓

Retest

"One issue I solved was Swagger becoming inaccessible after integrating Spring Security. I analyzed logs, identified endpoint restrictions, configured requestMatchers for Swagger URLs, and restored access while keeping business APIs protected



---------------------------------------------

# Challenges Faced & Solutions (Industry Learning)

## Challenge 1: EmployeeService Bean Not Found

### Error

```text
No qualifying bean of type 'EmployeeService' found
```

### Cause

The implementation class was outside the package scanned by Spring Boot, so Spring could not create the bean.

### Solution

Moved `EmployeeServiceImpl` into:

```text
com.aaruu.ems.serviceImpl
```

and added:

```java
@Service
```

### Learning

> Spring Boot scans only sub-packages of the main application class. Proper package structure is very important.

### Interview Answer

> We faced a bean creation issue because the implementation class was outside the component scanning path. After moving it to the correct package and using `@Service`, Spring created the bean successfully.

---

## Challenge 2: 403 Forbidden After Login

### Problem

Even after generating a JWT token, APIs were returning:

```text
403 Forbidden
```

### Cause

The request was not authorized properly according to `SecurityFilterChain` rules.

### Solution

Verified:

* JWT token in Authorization header
* User role (ADMIN/USER)
* `requestMatchers()` configuration
* JWT filter execution

### Learning

> Authentication means verifying who the user is, while authorization means checking what the user is allowed to do.

### Interview Answer

> We debugged a 403 issue by verifying JWT authentication, user roles, and SecurityFilterChain configurations to ensure proper authorization.

---

## Challenge 3: Multipart Request Error During File Upload

### Error

```text
Current request is not a multipart request
```

### Cause

Postman request body was sent as JSON instead of `form-data`.

### Solution

Changed Postman configuration:

```text
Body → form-data
Key → file
Type → File
```

### Learning

> Spring expects `MultipartFile` requests to be sent using multipart/form-data.

### Interview Answer

> We solved a MultipartException by changing the request type from JSON to form-data because Spring handles file uploads through multipart requests.

---

## Challenge 4: Redis Serialization Error

### Error

```text
DefaultSerializer requires a Serializable payload
```

### Cause

`EmployeeDto` did not implement `Serializable`, so Redis could not store the object.

### Solution

Added:

```java
implements Serializable
```

and:

```java
private static final long serialVersionUID = 1L;
```

### Learning

> Redis serializes Java objects into bytes before storing them in memory, so DTOs must implement Serializable.

### Interview Answer

> We faced a Redis serialization issue because the DTO was not Serializable. After implementing Serializable, Redis was able to cache the objects successfully.

---

## Challenge 5: Stale Cache Problem

### Problem

After deleting an employee from MySQL, the data still existed in Redis.

### Cause

The cache was not invalidated after DELETE and UPDATE operations.

### Solution

Added:

```java
@CacheEvict(
    value = "employees",
    key = "#id"
)
```

### Learning

> Cache and database must always stay synchronized. Otherwise, users may see old data.

### Interview Answer

> We used `@CacheEvict` to remove stale cache entries after update and delete operations, ensuring Redis always serves fresh data.

---

# Key Takeaways

```text
✅ Spring Bean Management

✅ Security Debugging (403 Issues)

✅ Multipart File Upload Handling

✅ Redis Serialization

✅ Cache Invalidation Strategy

✅ Real-world Problem Solving
```


