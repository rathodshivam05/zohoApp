package com.clayfin.entity;

import java.util.List;

import com.clayfin.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

	private String username;

	private String email;
	
	
	

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@OneToOne(mappedBy = "employee")
	@JsonIgnore
	private EmployeeProfile employeeProfile;

	@ManyToOne
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
	private List<Task> tasks;

	@OneToMany(mappedBy = "employee")
	@JsonIgnore
	private List<RegularizationRequest> reguralizations;

	@ElementCollection
	private List<String> skillSet;
	
	
}
