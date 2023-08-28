package com.clayfin.dto;

import lombok.Data;

@Data
public class EmployeeBenifits {
	
	private Double availableLeaves;
	private Double bookedLeaves;
	
	private Integer permisionHours;
	private Integer compansatoryLeaves;
	private Integer lOP;
	
}
