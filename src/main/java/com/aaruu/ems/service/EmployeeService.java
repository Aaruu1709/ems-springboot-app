
//We need a Service layer because we can keep business logic separate, which makes the code clean and easy to manage.
//We can also add validations, calculations, and business conditions in the Service layer

//Using a Service interface provides abstraction and loose coupling. It allows 
//us to change the implementation without affecting the controller layer
//and improves maintainability, testability, and scalability."

//employeeService and employeeServiveImpl

package com.aaruu.ems.service;

import java.util.List;

import com.aaruu.ems.entity.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Employee getEmployeeById(Integer id);

	Employee updateEmployee(Employee employee);

	void deleteEmployee(Integer id);
}
