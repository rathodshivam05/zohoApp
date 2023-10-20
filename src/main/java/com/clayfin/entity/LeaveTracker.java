package com.clayfin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class LeaveTracker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@JsonIgnore
	private Integer availablePrivilegeLeave;
	
	@JsonIgnore
	private Integer bookedPrivilegeLeave;
	
	@JsonIgnore
	private Integer lopTaken;
	
	@JsonIgnore
	private Integer compensatoryOff;
	
	@OneToOne
	@JsonIgnore
	private Employee employee;
	
}
