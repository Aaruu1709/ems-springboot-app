package com.aaruu.ems.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)

	public String handleEmployeeNotFoundException(EmployeeNotFoundException ex) {

		return ex.getMessage();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)

	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {

			errors.put(error.getField(), error.getDefaultMessage());

		});

		return errors;

	}

}

//Employee not found
//↓
//orElseThrow()
//↓
//throws EmployeeNotFoundException
//↓
//Spring automatically looks for a handler
//↓
//GlobalExceptionHandler catches it
//↓
//returns proper response (404 + message)