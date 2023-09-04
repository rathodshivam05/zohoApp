package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.clayfin.enums.RegularizationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

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
	
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	
	
	@JsonIgnore
	private LocalTime checkInTimestamp;
	
	private LocalTime CheckOutTimestamp;
	


	@ManyToOne
	private Employee employee;

	private LocalDateTime createdTimestamp;

}
