package com.clayfin.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Candidate {
	@Id
	@GeneratedValue
	private Integer candidateId;
	
	
	private String firstName;
	
	private String lastName;
	
	@Email
	private String email;
	private  Long mobileNumber;
	@Email
	private String alternateEmail;
	private Float experience;
	//@NotNull(message = "Candidate's resume cannot be empty.")

	private String status;
	
	private  Long alternateMobileNumber;
	

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate createdAt;

}
