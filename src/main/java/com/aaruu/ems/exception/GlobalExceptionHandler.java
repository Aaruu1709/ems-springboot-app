package com.aaruu.ems.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aaruu.ems.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)

	public ResponseEntity<ApiResponse<Object>> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {

		return ResponseEntity.status(404).body(new ApiResponse<>(ex.getMessage(), 404, null));
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//
//	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
//
//		Map<String, String> errors = new HashMap<>();
//
//		ex.getBindingResult().getFieldErrors().forEach(error -> {
//
//			errors.put(error.getField(), error.getDefaultMessage());
//
//		});
//
//		return errors;
//
//	}

	@ExceptionHandler(MethodArgumentNotValidException.class)

	public ResponseEntity<ApiResponse<Map<String, String>>>

			handleValidation(

					MethodArgumentNotValidException ex

	) {

		Map<String, String>

		errors = new HashMap<>();

		ex.getBindingResult()

				.getFieldErrors()

				.forEach(error ->

				errors.put(

						error.getField(),

						error.getDefaultMessage()

				)

				);

		return ResponseEntity

				.badRequest()

				.body(

						new ApiResponse<>(

								"Validation Failed",

								400,

								errors

						)

				);

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