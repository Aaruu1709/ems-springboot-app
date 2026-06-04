package com.aaruu.ems.dto;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;

public class EmmployeeDto {
	@org.hibernate.validator.constraints.NotBlank(message = "name should not be empty")
	private String firstName;
	@org.hibernate.validator.constraints.Email(message = "enter correct email")
	private String email;

	public EmmployeeDto(String firstName, String email) {
		super();
		this.firstName = firstName;
		this.email = email;
	}

	public EmmployeeDto() {
//		super();
	}

	public String getName() {
		return firstName;
	}

	public void setName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

//An object created only to send required data from backend to client.
//ATM does NOT show everything.
//ATM is acting like DTO 😄
//Database stores all.
//Customer sees limited data
//Why Industry Uses DTO=> resone: hide sensitive date and Reason 3 2 Database Change ≠ API Change

//Tomorrow entity changes:
//private String aadharNumber;
//DTO remains same.
//Frontend unaffected.

///"DTO is used to transfer only required data between backend and client. It helps hide sensitive fields, reduce unnecessary data exposure, and keep API responses clean."