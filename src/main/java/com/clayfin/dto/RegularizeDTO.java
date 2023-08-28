package com.clayfin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegularizeDTO {

	private LocalDate date;
	private LocalDateTime fromTime;
	private LocalDateTime toTime;
}
