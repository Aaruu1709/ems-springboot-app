package com.aaruu.ems.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String LastName;
	private String email;
	private String department;
	private double salary;

}
///ok so this is first step we created entity class for represent table..now we move towards repository (communicate with db)

//When Hibernate creates a table, it decides the column order internally.
//id is primary key and it is auto incremented
//JPA maps fields using column names, not column positions.
//As long as the correct columns exist and mappings are proper, column order does not affect application behavior

//i see in db tables ...my table name is differ than we mension here
//so i found that -> Hibernate automatically converts camelCase field names to snake_case column names by default