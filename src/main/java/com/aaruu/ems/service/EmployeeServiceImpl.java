//"Logger is used to record application events, 
//monitor execution flow, debug issues, and track errors.
//In Spring Boot we 
//commonly use SLF4J as abstraction and Logback as implementation

//logpack-actual tool to write lg
//slf4j-common way to write logs
//logger.info()-> SLF4J-> logback->console/file
package com.aaruu.ems.service;

import java.util.List;

//SLF4J means:
//Simple Logging Facade For Java
//Common logging interface
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aaruu.ems.dto.EmmployeeDto;
import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.exception.EmployeeNotFoundException;
import com.aaruu.ems.mapper.EmployeeMapper;
import com.aaruu.ems.repository.EmployeeRepository;
//Spring supports dependency injection using field injection, setter injection, and constructor injection.
//In modern Spring Boot applications,
//constructor injection is preferred because it improves testability and makes dependencies explicit."

import jakarta.transaction.Transactional;

//As a experienced developer, interviewers often prefer Constructor Injection over field injection (@Autowired on fields).

//@Service → tells Spring this is a Service bean.
//implements EmployeeService → forces us to implement all methods defined in the interface.

@Service
public class EmployeeServiceImpl implements EmployeeService {

	// simple developer language->what,when,where happend
	// methods of logger: info
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
//static:Avoid creating logger object repeatedly
//	logger logger->create space to write log
	// LoggerFactory->Factory creates logger object
	// EmployeeServiceImpl.class: attach logger to this class
	// Create one permanent logger for this class
//	to print application activity.

	private final EmployeeRepository employeeRepository;

	private final EmployeeMapper employeeMapper;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {

		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;

	}
	// here i created constructor of this class and injcted employeeservice class
	// insteed of @Autowiring i prefer to do constructor injection..bcoz it help us
	// to do code loosley coupled

	@Override
	public Employee saveEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}
	// This method receives an Employee object,
//	calls the Repository layer to save it in the database, and returns the saved Employee object.
//Then Repository talks to Hibernate → Hibernate generates SQL → MySQL stores the record.
	// The Repository layer interacts with the database. In Spring Data JPA,
	// Repository methods are implemented by Spring,
//	and Hibernate handles the SQL generation behind the scenes.

	@Override
	public List<EmmployeeDto> getAllEmployees() {

		List<Employee> employees = employeeRepository.findAll();

		return employees.stream().map(employeeMapper::toDto).toList();

	}
	// We can use the built-in findAll() method provided by JpaRepository. It
	// returns a List of entities.

	@Override
	public EmmployeeDto getEmployeeById(Integer id) {
		logger.info("Fetching employee with id {}", id);
		logger.warn("Employee not found {}", id);// warning->Data missing,unexpected result
		logger.error("Database connection failed");// for failure, exception or server issue

//		logger.debug(
//				"Employee object {}",
//				employee
//				);//developer debugging, local, testing

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		return employeeMapper.toDto(employee);
		// here we can also write orElseThrow(()->new RuntimeException("employee not
		// found"));
	}
	// Find employee by id. If found, return employee. Otherwise return null.
	// findById() returns an Optional<Entity>. It helps avoid NullPointerException
	// and provides a safe way to handle missing records.

	// In REST APIs, returning 404 Not Found is preferred because the requested
	// resource does not exist.
//    Returning 200 OK with null can be misleading
//First I would check whether the record exists using findById(). If the record is found, I would update it. Otherwise, 
//	I would return a 404 Not Found response
	@Override
	public Employee updateEmployee(Integer id, Employee employee) {

		Employee existingEmployee = employeeRepository.findById(id).orElse(null);
		if (existingEmployee == null) {
			return null;
//throw new RuntimeException("Employee not found");
		}

//		existingEmployee.setFirstName(employee.getFirstName());
//		existingEmployee.setLastName(employee.getLastName());
//		existingEmployee.setEmail(employee.getEmail());
//		existingEmployee.setDepartment(employee.getDepartment());
//		existingEmployee.setSalary(employee.getSalary());

		// mapper
		employeeMapper.updateEmployee(employee, existingEmployee);

		return employeeRepository.save(existingEmployee);
	}
	// JpaRepository uses the save() method for both insert and update operations.
	// If the entity already exists with a valid primary key, save() performs an
	// update

	@Override
	public void deleteEmployee(Integer id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id :" + id));

		employeeRepository.deleteById(id);
	}
	// JpaRepository provides the deleteById() method, which deletes a record based
	// on its primary key.

	@Override
	public Page<EmmployeeDto> getEmployees(int page, int size, String sortBy, String direction) {

		Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
//		return employees.map(employee -> new EmmployeeDto(employee.getFirstName(), employee.getEmail()));
		// mapper

		Sort sort =

				direction.equalsIgnoreCase("desc")

						?

						Sort.by(sortBy).descending()

						:

						Sort.by(sortBy).ascending();

		return employees.map(employeeMapper::toDto);
	}

	@Override
	public List<EmmployeeDto> searchEmployee(String keyword) {

		List<Employee> employees = employeeRepository
				.findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);

		return employees.stream().map(employeeMapper::toDto).toList();
	}

	@Override
	public List<EmmployeeDto> filterByDepartment(String department) {

		List<Employee> employees = employeeRepository.findByDepartmentIgnoreCase(department);
		return employees.stream().map(employeeMapper::toDto).toList();

	}

	@Override
	public Employee patchEmployee(Integer id, Employee employee) {

		Employee existingEmployee =

				employeeRepository.findById(id)

						.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		employeeMapper.patchEmployee(employee, existingEmployee);

		return employeeRepository.save(existingEmployee);

	}

	@Transactional
	@Override
	public void restoreEmployee(Integer id) {
		employeeRepository.restoreEmployee(id);
	}

}
