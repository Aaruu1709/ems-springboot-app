package com.aaruu.ems.serviceImpl;

import java.util.List;

//SLF4J means:
//Simple Logging Facade For Java
//Common logging interface
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aaruu.ems.dto.EmmployeeDto;
import com.aaruu.ems.entity.Employee;
import com.aaruu.ems.exception.EmployeeNotFoundException;
import com.aaruu.ems.kafka.event.EmployeeCreatedEvent;
import com.aaruu.ems.kafka.producer.KafkaProducerService;
import com.aaruu.ems.mapper.EmployeeMapper;
import com.aaruu.ems.repository.EmployeeRepository;
//Spring supports dependency injection using field injection, setter injection, and constructor injection.
//In modern Spring Boot applications,
//constructor injection is preferred because it improves testability and makes dependencies explicit."
import com.aaruu.ems.service.EmployeeService;
import com.aaruu.ems.service.FileStorageService;

import jakarta.transaction.Transactional;

//As a experienced developer, interviewers often prefer Constructor Injection over field injection (@Autowired on fields).

//@Service → tells Spring this is a Service bean.
//implements EmployeeService → forces us to implement all methods defined in the interface.

@Service
public class EmployeeServiceImpl implements EmployeeService {

// simple developer language->what,when,where happend
// methods of logger: info
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

// static:Avoid creating logger object repeatedly
// logger logger->create space to write log
// LoggerFactory->Factory creates logger object
// EmployeeServiceImpl.class: attach logger to this class
// Create one permanent logger for this class
// to print application activity.

	private final EmployeeRepository employeeRepository;

	private final EmployeeMapper employeeMapper;

	private final FileStorageService fileStorageService;

	private final KafkaProducerService kafkaProducerService;

//	@Autowired
//	private  FileStorageService fileStorageService;
// here insteed of autowred i prefer construction injection

//	private final FileStorageService fileStorageService;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
			FileStorageService fileStorageService, KafkaProducerService kafkaProducerService) {

		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
		this.fileStorageService = fileStorageService;
		this.kafkaProducerService = kafkaProducerService;

	}

// here i created constructor of this class and injcted employeeservice class
// insteed of @Autowiring i prefer to do constructor injection..bcoz it help us
// to do code loosley coupled

	@Override
	public Employee saveEmployee(Employee employee) {

		Employee savedEmployee = employeeRepository.save(employee);

		EmployeeCreatedEvent event = new EmployeeCreatedEvent(savedEmployee.getId(), savedEmployee.getFirstName(),
				savedEmployee.getEmail());

		kafkaProducerService.sendEmployeeCreatedEvent(event);

		logger.info("Employee created successfully with id: {}", savedEmployee.getId());

		return savedEmployee;
	}

// This method receives an Employee object,
// calls the Repository layer to save it in the database, and returns the saved
// Employee object.
// Then Repository talks to Hibernate → Hibernate generates SQL → MySQL stores
// the record.
// The Repository layer interacts with the database. In Spring Data JPA,
// Repository methods are implemented by Spring,
// and Hibernate handles the SQL generation behind the scenes.

	@Override
	public List<EmmployeeDto> getAllEmployees() {

		List<Employee> employees = employeeRepository.findByDeletedFalse();

		return employees.stream().map(employeeMapper::toDto).toList();

	}

// We can use the built-in findAll() method provided by JpaRepository. It
// returns a List of entities.

	@Override
	@Cacheable(value = "employees", key = "#id")
	public EmmployeeDto getEmployeeById(Integer id) {

		logger.info("Fetching Employee from DATABASE");
		logger.info("Fetching employee with id {}", id);

		// logger.warn("Employee not found {}", id);// warning->Data missing,unexpected
		// result
		// logger.error("Database connection failed");// for failure, exception or
		// server issue

		// logger.debug(
		// "Employee object {}",
		// employee
		// );//developer debugging, local, testing

		Employee employee = employeeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> {
			logger.warn("Employee not found with id: {}", id);

			return new EmployeeNotFoundException("Employee not found with id: " + id);
		});

		return employeeMapper.toDto(employee);

	}

// Find employee by id. If found, return employee. Otherwise return null.
// findById() returns an Optional<Entity>. It helps avoid NullPointerException
// and provides a safe way to handle missing records.

// In REST APIs, returning 404 Not Found is preferred because the requested
// resource does not exist.
// Returning 200 OK with null can be misleading
// First I would check whether the record exists using findById(). If the record
// is found, I would update it. Otherwise,
// I would return a 404 Not Found response

	@Override
	@CacheEvict(value = "employees", key = "#id")
//	@CachePut(value="employees", key="#id")
	public Employee updateEmployee(Integer id, Employee employee) {

		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		// existingEmployee.setFirstName(employee.getFirstName());
		// existingEmployee.setLastName(employee.getLastName());
		// existingEmployee.setEmail(employee.getEmail());
		// existingEmployee.setDepartment(employee.getDepartment());
		// existingEmployee.setSalary(employee.getSalary());

		// mapper
		employeeMapper.updateEmployee(employee, existingEmployee);

		logger.info("Employee updated successfully with id: {}", id);

		return employeeRepository.save(existingEmployee);
	}

// JpaRepository uses the save() method for both insert and update operations.
// If the entity already exists with a valid primary key, save() performs an
// update

	@Override
	@CacheEvict(value = "employees", key = "#id")
	public void deleteEmployee(Integer id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		employee.setDeleted(true);
		employeeRepository.save(employee);

		logger.info("Employee soft deleted successfully with id: {}", id);
	}

// JpaRepository provides the deleteById() method, which deletes a record based
// on its primary key.

	@Override
	public Page<EmmployeeDto> getEmployees(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		// Fetch only active employees while applying pagination and sorting.
		Page<Employee> employees = employeeRepository.findByDeletedFalse(PageRequest.of(page, size, sort));

		return employees.map(employeeMapper::toDto);
	}

	@Override
	public List<EmmployeeDto> searchEmployee(String keyword) {

		// Search only active employees by first name or email.
		List<Employee> employees = employeeRepository.searchActiveEmployees(keyword);

		// log.info("user found....");

		return employees.stream().map(employeeMapper::toDto).toList();
	}

	@Override
	public List<EmmployeeDto> filterByDepartment(String department) {

		// Filter only active employees by department.
		List<Employee> employees = employeeRepository.findByDeletedFalseAndDepartmentIgnoreCase(department);

		return employees.stream().map(employeeMapper::toDto).toList();

	}

	@Override
	public Employee patchEmployee(Integer id, Employee employee) {

		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		employeeMapper.patchEmployee(employee, existingEmployee);

		logger.info("Employee partially updated successfully with id: {}", id);

		return employeeRepository.save(existingEmployee);

	}

	@Transactional
	@Override
	public void restoreEmployee(Integer id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		employee.setDeleted(false);

		employeeRepository.save(employee);

		logger.info("Employee restored successfully with id: {}", id);
	}

	@Override
	public String uploadEmployeePhoto(Integer id, MultipartFile file) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		String photoUrl = fileStorageService.uploadPhoto(file);

		employee.setPhotoUrl(photoUrl);

		employeeRepository.save(employee);

		logger.info("Employee photo uploaded successfully for id: {}", id);

		return photoUrl;
	}

	@Override
	public String uploadEmployeeResume(Integer id, MultipartFile file) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		String resumeUrl = fileStorageService.uploadResume(file);

		employee.setResumeUrl(resumeUrl);

		employeeRepository.save(employee);

		logger.info("Employee resume uploaded successfully for id: {}", id);

		return resumeUrl;
	}

	@Override
	public byte[] getEmployeePhoto(Integer id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		return fileStorageService.getFile(employee.getPhotoUrl());
	}

	@Override
	public byte[] getEmployeeResume(Integer id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

		return fileStorageService.getFile(employee.getResumeUrl());
	}

}
