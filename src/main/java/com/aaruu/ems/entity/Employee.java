package com.aaruu.ems.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "name should not be empty")
	private String firstName;
	private String lastName;
	@Email(message = "plz type correct email")
	private String email;
	private String department;
	private Double salary;

	public Employee(Integer id, String firstName, String lastName, String email, String department, Double salary) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.salary = salary;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

}
///ok so this is first step we created entity class for represent table..now we move towards repository (communicate with db)

//When Hibernate creates a table, it decides the column order internally.
//id is primary key and it is auto incremented
//JPA maps fields using column names, not column positions.
//As long as the correct columns exist and mappings are proper, column order does not affect application behavior

//i see in db tables ...my table name is differ than we mension here
//so i found that -> Hibernate automatically converts camelCase field names to snake_case column names by default