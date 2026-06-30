package com.aaruu.ems.dto;

import java.io.Serializable;

import lombok.Builder;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;

@Builder

public class EmmployeeDto implements Serializable {

	private static final long serialVersionUID = 1L;

//	@NotEmpty(message = "please enter your name")
	private String firstName;
//	@Email(message = "emial should be correcr")
	private String email;

	public EmmployeeDto(String firstName, String email) {
		super();
		this.firstName = firstName;
		this.email = email;
	}

	public EmmployeeDto() {
//		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
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