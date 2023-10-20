package com.clayfin.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayDto {
	
	private String holiday;
	
	private LocalDate date;
}
