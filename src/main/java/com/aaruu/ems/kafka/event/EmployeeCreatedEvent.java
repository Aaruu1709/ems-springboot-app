package com.aaruu.ems.kafka.event;

public class EmployeeCreatedEvent {

	private Integer employeeId;
	private String firstName;
	private String email;

	public EmployeeCreatedEvent() {
	}

	public EmployeeCreatedEvent(Integer employeeId, String firstName, String email) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.email = email;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	@Override
	public String toString() {
		return "EmployeeCreatedEvent{" + "employeeId=" + employeeId + ", firstName='" + firstName + '\'' + ", email='"
				+ email + '\'' + '}';
	}
}