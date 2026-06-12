package com.aaruu.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
// activates Spring Data JPA's auditing capabilities.
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}

//
//GET /employees/90
//↓
//Controller
//↓
//Service
//↓
//findById()
//↓
//Employee not found
//↓
//orElseThrow()
//↓
//EmployeeNotFoundException
//↓
//GlobalExceptionHandler
//↓
//"Employee not found with id : 90"