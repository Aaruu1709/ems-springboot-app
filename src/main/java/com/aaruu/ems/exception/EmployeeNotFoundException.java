package com.aaruu.ems.exception;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(String message) {
		super(message);
	}

}

//I used RuntimeException because EmployeeNotFoundException is a custom business exception. 
//By extending RuntimeException, it becomes an unchecked exception, 
//so I don't need to declare it using throws in every method. Spring's 
//global exception handler can handle it centrally, 
//which keeps the code cleaner

//| Checked Exception                | Unchecked Exception        |
//| -------------------------------- | -------------------------- |
//| Must handle                      | Optional to handle         |
//| Compile-time checking            | Runtime checking           |
//| `IOException`                    | `NullPointerException`     |
//| `SQLException`                   | `ArithmeticException`      |
//| Requires `try-catch` or `throws` | No requirement             |
//| Extends `Exception`              | Extends `RuntimeException` |

//
//"@RestControllerAdvice is used for global exception handling "
//+ "in REST APIs. It allows us to handle exceptions from all "
//+ "controllers in one place. Inside it, we use @ExceptionHandler to 
//handle specific exception types such as EmployeeNotFoundException. 
//This helps return meaningful error"
//+ " messages and proper HTTP status codes like 404 Not Found.