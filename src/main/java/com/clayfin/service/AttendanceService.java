package com.clayfin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.clayfin.entity.Attendance;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;

public interface AttendanceService {

	Attendance checkInAttendance(Integer employeeId) throws EmployeeException,AttendanceException;
	
	Attendance checkOutAttendance(Integer employeeId) throws EmployeeException,AttendanceException;
	
	Boolean isAttendanceExist(Integer attendanceId)throws AttendanceException;

	List<Attendance> getAttendanceByDateAndEmployeeId(LocalDate date, Integer employeeId)
			throws EmployeeException, AttendanceException;

	List<Attendance> getAttendanceByEmployeeId(Integer employeeId) throws AttendanceException, EmployeeException;

	Attendance updateAttendance(Integer attendanceId,Attendance attendance) throws AttendanceException;

	Attendance deleteAttendance(Integer attendanceId) throws AttendanceException;
	
	
	Attendance regularize(Integer employeeId,LocalDate date,LocalDateTime fromTime,LocalDateTime toTime) throws AttendanceException,EmployeeException;

	
}
