package com.clayfin.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.clayfin.enums.ProbationStatus;
import com.clayfin.enums.RoleType;
import com.clayfin.enums.TitleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_id")
	private Integer employeeId;
	
	@NotBlank(message = "User's name Should n't be empty.")
	@Pattern(regexp = "^[A-Za-z]{5,15}$",message = "User's name Should Be  minimum 5 characters and maximum 15 characters")		
	private String username;
	
	@NotBlank(message = "User's alternate email cannot be empty.")
	@Email
	@Column(unique = true)
	private String email;

	private String reportingTo;
	
	@Enumerated(EnumType.STRING)
	private TitleType Title;
		
	private LocalDate joiningDate;
	
	private ProbationStatus probationStatus;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$",
			message = " User's Password Should Be >7 and < 15 Characters and Atleast One Upper Case "
					+ "and Atleast One Lower Case and Atleast One Digit and Atleast One Special Character" )
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Enumerated(EnumType.STRING)
	@NotBlank(message = "User's role Should n't be empty.")
	private RoleType role;
	
	@OneToOne(mappedBy = "employee")
	@JsonIgnore
	private EmployeeProfile employeeProfile;

	@ManyToOne
	@JsonIgnore
	private Employee manager;

	@JsonIgnore
	@OneToMany(mappedBy = "manager")
	private List<Employee> subEmployees;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<Attendance> attendances;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<LeaveRecord> leaveRecords;
	
	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<Claims> claims;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<Task> tasks;

	@OneToMany(mappedBy = "employee")
	@JsonIgnore
	private List<RegularizationRequest> reguralizations;

	@ElementCollection
	@JsonIgnore
	private List<String> skillSet;

	@OneToOne(mappedBy = "employee")
	@JsonIgnore
	private LeaveTracker leaveTracker;

	
	
	
	

}