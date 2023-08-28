package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.clayfin.enums.RegularizationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RegularizationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer reguralizationId;

	@Enumerated(EnumType.STRING)
	private RegularizationStatus status;
	
	private LocalDate date;
	private LocalDateTime checkInTimestamp;
	private LocalDateTime CheckOutTimestamp;
	


	@ManyToOne
	private Employee employee;

	private LocalDateTime createdTimestamp;

}
