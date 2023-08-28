package com.clayfin.entity;

import java.time.LocalDateTime;

import com.clayfin.enums.TaskStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;
	private String taskDescription;
	private LocalDateTime assignedTime;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	@ManyToOne
	@JoinColumn(name="assinged_employee")
	private Employee employee;
	
	

}
