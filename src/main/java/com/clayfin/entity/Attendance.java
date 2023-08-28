package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private LocalDateTime checkInTimestamp;
	private LocalDateTime CheckOutTimestamp;
	private LocalTime spentHours;
	
	

	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;
	

}
