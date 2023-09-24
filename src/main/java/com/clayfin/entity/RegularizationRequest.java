package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.clayfin.enums.RegularizationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

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
