package com.clayfin.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clayfin.enums.AttendanceStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DayAttendanceDto {
	private LocalTime totalHoursInADay;
	private LocalDate date;
	private String status;
}
