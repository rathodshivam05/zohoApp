package com.clayfin.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
//  import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Candidate {
	@Id
	@GeneratedValue
	private Integer candidateId;
	
	@NotBlank(message = "Candidates's first name Should n't be empty.")
	private String firstName;
	
	private String lastName;
	
	@NotBlank(message = "Candidate's email cannot be empty.")
	@Email
	@Column(unique=true)
	private String email;
	
	//@Pattern(regexp="^[+]91[6789]\\d{9}$",message="Candidate's mobile no should start with +91 and should be 10digits ")
    @Min(value=1,message=" mobile number can't be -ve ")
    @NotNull(message = "Mobile Number can't be empty")
	private  Long mobileNumber;
	@Email
	private String alternateEmail;
	private Float experience;
	
	private String status;
	
	//@Pattern(regexp="^[+]91[6789]\\d{9}$",message="Candidate's mobile no should start with +91 and should be 10digits ")
    @Min(value=1,message=" mobile number can't be -ve ")
	private  Long alternateMobileNumber;
	

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate createdAt;

}
