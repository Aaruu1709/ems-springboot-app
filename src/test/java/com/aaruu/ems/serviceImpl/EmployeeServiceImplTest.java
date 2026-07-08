package com.aaruu.ems.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aaruu.ems.dto.EmmployeeDto;
import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.exception.EmployeeNotFoundException;
import com.aaruu.ems.mapper.EmployeeMapper;
import com.aaruu.ems.repository.EmployeeRepository;
import com.aaruu.ems.service.FileStorageService;

@ExtendWith(MockitoExtension.class)
//enable mokito suppoer for this class, without this @mock is not wokring
class EmployeeServiceImplTest {

	@Mock
//	create fake repository , no mysql call
	private EmployeeRepository employeeRepository;

	@Mock
	private EmployeeMapper employeeMapper;

	@Mock
	private FileStorageService fileStorageService;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	void testGetEmployeeById() {

		Employee employee = new Employee();
		employee.setFirstName("aaruu");
		employee.setEmail("aaruu@gmail.com");

		EmmployeeDto emmployeeDto = new EmmployeeDto();
		emmployeeDto.setFirstName("aaruu");
		emmployeeDto.setEmail("aaruu@gmail.com");

		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

		when(employeeMapper.toDto(employee)).thenReturn(emmployeeDto);

		EmmployeeDto result = employeeService.getEmployeeById(1);

		assertEquals("aaruu", result.getFirstName());

	}

	@Test
	void shouldThrowExceptionWhenEmployeeNotFound() {

		// Arrange
		when(employeeRepository.findById(1)).thenReturn(Optional.empty());

		// Act + Assert
		EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
				() -> employeeService.getEmployeeById(1));

		assertEquals("Employee not found with id :1", exception.getMessage());
	}

	@Test
	void shouldSaveEmployee() {

		// Arrange
		Employee employee = new Employee();

		employee.setFirstName("Aaruu");
		employee.setEmail("aaruu@gmail.com");

		when(employeeRepository.save(employee)).thenReturn(employee);

		// Act
		Employee savedEmployee = employeeService.saveEmployee(employee);

		// Assert
		assertEquals("Aaruu", savedEmployee.getFirstName());

		// Verify
		// Check whether save(employee) was actually called.
		verify(employeeRepository).save(employee);
	}

}