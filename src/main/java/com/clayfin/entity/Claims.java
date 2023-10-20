package com.clayfin.entity;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Claims {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	
	@ManyToOne
	@JsonIgnore
	private Employee employee;
	
	//int employeeId;
	private LocalDate addedTime;
	private String category;
	private Integer connectionNumber;
	private String currency;
	private Date toPeriod;
	private Date expenseDate;
	private Integer amount;
	private String remarks;
	private Blob bill;
	private Double amountPaid;
	private Integer currencyPaid;
	private Date paymentDate;
	private Integer modeOfPayment;
	private String status;
	private String claimType;
	private String expenseDescription;
}