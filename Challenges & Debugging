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
