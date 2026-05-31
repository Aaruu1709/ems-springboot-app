package com.aaruu.ems.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//role of controller-"The Controller layer handles incoming HTTP requests, calls the Service layer
//to process business logic, and returns responses to the client.
import org.springframework.web.bind.annotation.RestController;

import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.service.EmployeeService;
//@RestController
//It tells Spring Boot that this class will handle REST API requests and return data (usually JSON) to the client
//@RestController is a combination of @Controller and @ResponseBody.
//It is used to create RESTful web services and automatically converts Java objects into JSON responses

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;

	}

	@PostMapping
	public Employee saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	// @RequestBody is used to bind the JSON data from the HTTP request body to a
	// Java object.

}

//Controller should depend on the Service interface rather than the implementation.
//This promotes loose coupling and makes the application easier to maintain and extend
//Postman
//↓
//Controller
//↓
//Service (Interface)
//↓
//ServiceImpl
//↓
//Repository
//↓
//MySQL