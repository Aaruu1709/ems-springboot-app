package com.aaruu.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.aaruu.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String email);

	List<Employee> findByDepartmentIgnoreCase(String department);

	@Modifying
	@Query("""

			UPDATE Employee e

			SET e.deleted=false

			WHERE e.id=:id

			""")
	void restoreEmployee(Integer id);
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