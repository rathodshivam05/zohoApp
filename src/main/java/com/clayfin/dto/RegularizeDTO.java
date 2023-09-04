package com.clayfin.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class RegularizeDTO {

	private LocalDate date;
	private LocalTime fromTime;
	private LocalTime toTime;
}
