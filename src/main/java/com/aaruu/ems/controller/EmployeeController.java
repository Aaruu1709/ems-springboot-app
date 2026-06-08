package com.aaruu.ems.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//role of controller-"The Controller layer handles incoming HTTP requests, calls the Service layer
//to process business logic, and returns responses to the client.
import org.springframework.web.bind.annotation.RestController;

import com.aaruu.ems.dto.EmmployeeDto;
import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.service.EmployeeService;
//@RestController
//It tells Spring Boot that this class will handle REST API requests and return data (usually JSON) to the client
//@RestController is a combination of @Controller and @ResponseBody.
//It is used to create RESTful web services and automatically converts Java objects into JSON responses

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;

	}

	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
		Employee e = employeeService.saveEmployee(employee);
		return ResponseEntity.status(201).body(e);
	}
	// Return HTTP status code 201 Created.
	// Return the saved Employee object in the response body.
	// @RequestBody is used to bind the JSON data from the HTTP request body to a
	// Java object.

	@GetMapping("/allEmployees")
	public List<EmmployeeDto> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
//@PathVariable is used to extract values from the URL and bind them to method parameters.
//	| Annotation      | Used For                            |
//	| --------------- | ----------------------------------- |
//	| `@RequestBody`  | Reads data from request body (JSON) |
//	| `@PathVariable` | Reads data from URL path            |

	// @PathVariable is used to extract dynamic values from the URL and bind them to
	// method parameters
	// @RequestBody-It receives JSON from the request body and converts it into a
	// Java object.

	@GetMapping("/{id}")
	public ResponseEntity<EmmployeeDto> getEmployeeById(@PathVariable Integer id) {
		EmmployeeDto employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee);
	}

	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {

		return employeeService.updateEmployee(id, employee);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Integer id) {

		employeeService.deleteEmployee(id);

	}

	@GetMapping("/page")
	public ResponseEntity<Page<EmmployeeDto>> getEmployee(@RequestParam int page, @RequestParam int size,
			@RequestParam String sortBy) {
		Page<EmmployeeDto> employees = employeeService.getEmployees(page, size, sortBy);

		return ResponseEntity.ok(employees);

	}

	@GetMapping("/search")
	public ResponseEntity<List<EmmployeeDto>> searchEmployee(@RequestParam String keyword) {

		List<EmmployeeDto> employees = employeeService.searchEmployee(keyword);
		return ResponseEntity.ok(employees);

	}
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

//---------------------------------------------------

//"I used Swagger in my project for API documentation and testing. "
//+ "It provides all APIs in one place with a UI, so developers and "
//+ "testers can execute requests, pass input values, "
//+ "and verify responses without manually creating requests.

//----------------------------------------------

//"Swagger is used to document and test APIs."
//+ " It shows all endpoints in one place and allows us "
//+ "to execute APIs directly from the browser