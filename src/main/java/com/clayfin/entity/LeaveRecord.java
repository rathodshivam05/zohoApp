package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LeaveRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer leaveId;
	private int days;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime createdTimestamp;

	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	@Enumerated(EnumType.STRING)
	private LeaveStatus status;

	private String reason;
	
	private String response;
	
	@ManyToOne
	private Employee employee;
	
	private Integer managerId;

}
