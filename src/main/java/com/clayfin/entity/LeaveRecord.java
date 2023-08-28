package com.clayfin.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;

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

	@ManyToOne
	private Employee employee;

}
