package com.aaruu.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.repository.EmployeeRepository;

//Spring supports dependency injection using field injection, setter injection, and constructor injection.
//In modern Spring Boot applications,
//constructor injection is preferred because it improves testability and makes dependencies explicit."

//As a experienced developer, interviewers often prefer Constructor Injection over field injection (@Autowired on fields).

//@Service → tells Spring this is a Service bean.
//implements EmployeeService → forces us to implement all methods defined in the interface.

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {

		this.employeeRepository = employeeRepository;
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
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	// We can use the built-in findAll() method provided by JpaRepository. It
	// returns a List of entities.

	@Override
	public Employee getEmployeeById(Integer id) {
		return employeeRepository.findById(id).orElse(null);
	}
	// Find employee by id. If found, return employee. Otherwise return null.
	// findById() returns an Optional<Entity>. It helps avoid NullPointerException
	// and provides a safe way to handle missing records.

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	// JpaRepository uses the save() method for both insert and update operations.
	// If the entity already exists with a valid primary key, save() performs an
	// update

	@Override
	public void deleteEmployee(Integer id) {
		employeeRepository.deleteById(id);
	}
	// JpaRepository provides the deleteById() method, which deletes a record based
	// on its primary key.

}
