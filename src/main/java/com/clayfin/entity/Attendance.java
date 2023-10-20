package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Attendance{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer attendanceId;
	private LocalDate date;
	
	
	private LocalTime checkInTimestamp;
	
	private LocalTime CheckOutTimestamp;
	private LocalTime spentHours;
	
	private Boolean checkin;
	
	@ManyToOne
	@JoinColumn(name="employee_id")
	@JsonIgnore
	private Employee employee;
	

}
