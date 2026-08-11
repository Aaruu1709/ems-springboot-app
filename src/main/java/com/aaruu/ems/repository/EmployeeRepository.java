package com.aaruu.ems.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aaruu.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String email);

	List<Employee> findByDepartmentIgnoreCase(String department);

//	@Modifying
//	@Query("""
//
//			UPDATE Employee e
//
//			SET e.deleted=false
//
//			WHERE e.id=:id
//
//			""")
//	void restoreEmployee(@Param("id") Integer id);

	List<Employee> findByDeletedFalse();
// Give only active employees

// Give only active employees with pagination and sorting
	Page<Employee> findByDeletedFalse(Pageable pageable);

// Search only active employees by first name or email
	@Query("""
			SELECT e FROM Employee e
			WHERE e.deleted = false
			AND (
				LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
				OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
			)
			""")
	List<Employee> searchActiveEmployees(@Param("keyword") String keyword);

// Filter only active employees by department
	List<Employee> findByDeletedFalseAndDepartmentIgnoreCase(String department);

	Optional<Employee> findByIdAndDeletedFalse(Integer id);

}

//The first generic type is the Entity class managed by the repository,
//and the second generic type is the data type of the Entity's primary key

//JpaRepository provides built-in CRUD operations and additional database functionalities.
//It reduces boilerplate code because we don't need to write SQL queries or DAO implementations for basic operations

//Spring Boot automatically gives us:
//save()
//findAll()
//findById()
//deleteById()
//existsById()
//count() and many more

//JpaRepository provides the findById() method.
//It returns an Optional<Entity>, which helps handle cases where the record is not found.
