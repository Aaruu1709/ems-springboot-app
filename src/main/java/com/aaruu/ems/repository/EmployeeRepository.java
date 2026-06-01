package com.aaruu.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaruu.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

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