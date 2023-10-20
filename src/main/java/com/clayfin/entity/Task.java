package com.clayfin.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.clayfin.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;
	private String taskDescription;
	private LocalDateTime assignedTime;
	private LocalDateTime deadLineTime;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	@ManyToOne()
	@JoinColumn(name="assinged_employee")
	@JsonIgnore
	private Employee employee;
	
	private String employeeName;

}
