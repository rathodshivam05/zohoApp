package com.clayfin.entity;

import java.time.LocalDate;

import com.clayfin.enums.ActiveStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class EmployeeProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_profile_id")
	private Integer employeeProfileId;
	
	private String title;
	
	private String department;
	
	private String baseLocation;
	
	private String workLocation;
	
	private Double experience;
	
	private Integer noticePeriod;
	
	private String probationStatus;
	
	private String pan;
	
	private String nameAsPan;
	
	private Double aadharNumber;
	
	private Double bankAcNumber;
	
	private String bankName;
	
	private String ifsc;
	
	private String branch;
	
	private String educationalQualification;
	
	private String fatherName;
	
	private Double passportNumber;
	
	private LocalDate dateOfJoining;
	
	private Double mobileNumber;
	
	private String otherEmail;
	
	private Double age;
	
	private LocalDate birthDate;
	
	private String gender;
	
	private String MaritalStatus;
	
	private String aboutMe;
	
	private String permanentAddress;
	
	private String presentAddress;
	
	private String emergencyContactPerson;
	
	private Double emergencyContactNumber;
	
	private String relationshipOfEmergencyContact;
	
	private String bloodGroup;
	
	private LocalDate dateOfResignation;
	
	private String covidVaccinationStatus;
	
	private String vaccineType;
	
	private LocalDate firstDosage;
	
	private LocalDate secondDosage;
	
	private String previousCompanyName;
	
	private String previousJobTitle;
	
	private LocalDate previousJobFromdate;
	
	private LocalDate previousJobTodate;
	
	private String previousJobDescription;
	
	private String previousJobRelievingLetter;
	
	private String collegeName;
	
	private String degree;
	
	private String fieldOfStudy;
	
	private LocalDate dateOfCompletion;
	
	private String additionalNotes;
	
	private String personalQualification;
	
	private String personalQualificationSpecialization;
	
	private String personalQualificationUniversity;
	
	private LocalDate personalQualificationDateofCompletion;
	
	private String personalQualificationAdditionalNotes;
	
	private String dependentName;
	
	private String dependentRelationship;
	
	private LocalDate dependentDateOfBirth;
	
	private String employeeType;
	
	private ActiveStatus status;
	
	private String reportingTo;
	

	private String nationality;
	
	private String religion;
	

	@OneToOne()
	private Employee employee;
	
	
	
	
	
	
	

}
