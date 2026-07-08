package com.aaruu.ems.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//role of controller-"The Controller layer handles incoming HTTP requests, calls the Service layer
//to process business logic, and returns responses to the client.
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aaruu.ems.dto.EmmployeeDto;
import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.service.EmployeeService;
import com.aaruu.ems.service.FileStorageService;
import com.aaruu.ems.serviceImpl.FileStorageServiceImpl;
//@RestController
//It tells Spring Boot that this class will handle REST API requests and return data (usually JSON) to the client
//@RestController is a combination of @Controller and @ResponseBody.
//It is used to create RESTful web services and automatically converts Java objects into JSON responses
import com.aaruu.ems.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
@Tag(name = "employee managemet sytem", description = "API for managing employee")

@SecurityRequirement(name = "Bearer Authentication")

public class EmployeeController {

	private final EmployeeService employeeService;
	private final FileStorageService fileStorageService;
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	// log level: TRACE->DEBUG->INFO->WARN->ERROR
	// "We use logging to monitor application behavior, track requests,
//	debug issues, and troubleshoot production problems. Instead of using
//	System.out.println(), 
//	we use SLF4J with different log levels such as INFO, WARN, and ERROR."

	public EmployeeController(EmployeeService employeeService, FileStorageService fileStorageService,
			FileStorageServiceImpl fileStorageServiceImpl) {
		this.employeeService = employeeService;
		this.fileStorageService = fileStorageService;

	}

	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {

		Employee emp = employeeService.saveEmployee(employee);
		log.error("failed to save employee");

		log.info("Create Employee ApI called");// basic log
		log.info("Employee Email: {}", employee.getEmail());
		log.info("fetching employee with id: {}", employee.getLastName() + "," + employee.getId());
		return ResponseEntity.status(201).body(emp);
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
	@Operation(summary = "Get Employee By ID", description = "Fetch employee details using employee ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Employee fetched successfully"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access") })
	public ResponseEntity<com.aaruu.ems.payload.ApiResponse<EmmployeeDto>> getEmployeeById(@PathVariable Integer id) {

		EmmployeeDto employee = employeeService.getEmployeeById(id);

		log.info("Find Employee API called: {}", id);

		return ResponseEntity.ok(ResponseUtil.success("Employee fetched successfully", employee));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Employee", description = "Update employee details using employee ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "400", description = "Validation failed") })

	public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {

		log.info("Create Employee API called:update emplyee");

		return employeeService.updateEmployee(id, employee);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Employee", description = "Soft delete an employee using employee ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Employee not found") })
	public void deleteEmployeeById(@PathVariable Integer id) {
		log.warn("Warning:are you sure to delte emplyee");
		log.warn("Deleting employee with id : {}", id);

		employeeService.deleteEmployee(id);
		log.warn("Employee deleted successfully id : {}", id);

	}

	@GetMapping("/page")
	public ResponseEntity<Page<EmmployeeDto>> getEmployee(@RequestParam int page, @RequestParam int size,
			@RequestParam String sortBy, @RequestParam String direction) {
		Page<EmmployeeDto> employees = employeeService.getEmployees(page, size, sortBy, direction);

		return ResponseEntity.ok(employees);

	}

	@GetMapping("/search")
	public ResponseEntity<List<EmmployeeDto>> searchEmployee(@RequestParam String keyword) {

		List<EmmployeeDto> employees = employeeService.searchEmployee(keyword);
		return ResponseEntity.ok(employees);

	}

	@GetMapping("/filter")
	public ResponseEntity<List<EmmployeeDto>> filterByDepartment(@RequestParam String department) {

		List<EmmployeeDto> employees = employeeService.filterByDepartment(department);
		return ResponseEntity.ok(employees);

	}

	@PatchMapping("/{id}")

	public ResponseEntity<Employee>

			patchEmployee(

					@PathVariable Integer id,

					@RequestBody Employee employee

	) {

		Employee updated =

				employeeService.patchEmployee(id, employee);

		return ResponseEntity.ok(updated);

	}

	@PatchMapping("/restore/{id}")
	public ResponseEntity<?> restore(@PathVariable Integer id) {
		System.out.println("RESTORE API ENTERED");

		employeeService.restoreEmployee(id);
		return ResponseEntity.ok(ResponseUtil.success("Employee restored", null));
	}

	@PostMapping("/upload/photo/{id}")
	public ResponseEntity<String> uploadPhoto(

			@PathVariable Integer id,

			@RequestParam("file") MultipartFile file

	) {

		System.out.println("UPLOAD API ENTERED 🔥");

		String photoUrl = employeeService.uploadEmployeePhoto(id, file);

		return ResponseEntity.ok(photoUrl);

	}

	@PostMapping("/upload/resume/{id}")
	public ResponseEntity<String> uploadResume(

			@PathVariable Integer id,

			@RequestParam("file") MultipartFile file

	) {

		String resumeUrl = employeeService.uploadEmployeeResume(id, file);

		return ResponseEntity.ok(resumeUrl);
	}

	@GetMapping("/photo/{id}")
	public ResponseEntity<byte[]> getPhoto(@PathVariable Integer id) {

		byte[] photo = employeeService.getEmployeePhoto(id);

		return ResponseEntity.ok().header("Content-Type", "image/jpeg").body(photo);
	}

	@GetMapping("/resume/{id}")
	public ResponseEntity<byte[]> getResume(@PathVariable Integer id) {

		byte[] resume = employeeService.getEmployeeResume(id);

		return ResponseEntity.ok().header("Content-Type", "application/pdf")
				.header("Content-Disposition", "attachment; filename=resume.pdf").body(resume);
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