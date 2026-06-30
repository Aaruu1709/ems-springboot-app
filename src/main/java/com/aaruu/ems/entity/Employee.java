package com.aaruu.ems.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@SQLDelete(sql = "UPDATE employees SET deleted=true WHERE id=?")
//@SQLRestriction("deleted=false")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employees")
public class Employee {

	// @NotEmpty
//	→ allows spaces
//
//	@NotBlank
//	→ blocks spaces too

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "name should not be empty")
	private String firstName;
	private String lastName;
	@NotBlank(message = "Email should not be empty")

	@Email(message = "Please enter valid email")
	private String email;
	private String department;
	private Double salary;

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Column(nullable = false)
	private Boolean deleted = false;
	@CreatedBy
	private String createdBy;// Who created this employee?
	@LastModifiedBy
	private String updatedBy;// Who updated this employee last?

	private String photoUrl;
	private String resumeUrl;

	public Employee(Integer id, String firstName, String lastName, String email, String department, Double salary,
			String createdBy, String updatedBy, String photoUrl, String resumeUrl) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.salary = salary;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.photoUrl = photoUrl;
		this.resumeUrl = resumeUrl;

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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

}
///ok so this is first step we created entity class for represent table..now we move towards repository (communicate with db)

//When Hibernate creates a table, it decides the column order internally.
//id is primary key and it is auto incremented
//JPA maps fields using column names, not column positions.
//As long as the correct columns exist and mappings are proper, column order does not affect application behavior

//i see in db tables ...my table name is differ than we mension here
//so i found that -> Hibernate automatically converts camelCase field names to snake_case column names by default

//--------------------------------------------

//no need log factory only need lombok 
//import lombok.extern.slf4j.Slf4j;

//@Slf4j
//@Service
//public class EmployeeServiceImpl {
//
//    public void saveEmployee() {
//
//        log.info("Employee saved");
//
//    }
//}
//
//That's it.
//
//No need for:
//
//private static final Logger log =
//        LoggerFactory.getLogger(...);

//----------------------------
//The data remains in the database, and @SQLRestriction hides it from normal queries.