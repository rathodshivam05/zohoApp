package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.clayfin.dto.DayAttendanceDto;
import com.clayfin.entity.Attendance;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;

public interface AttendanceService {

	Attendance checkInAttendance(Integer employeeId,Principal user) throws EmployeeException,AttendanceException;
	
	Attendance checkOutAttendance(Integer employeeId,Principal user) throws EmployeeException,AttendanceException;
	
	Boolean isAttendanceExist(Integer attendanceId,Principal user)throws AttendanceException, EmployeeException;

	List<Attendance> getAttendanceByDateAndEmployeeId(LocalDate date, Integer employeeId,Principal user)
			throws EmployeeException, AttendanceException;

	List<Attendance> getAttendanceByEmployeeId(Integer employeeId,Principal user) throws AttendanceException, EmployeeException;

	Attendance updateAttendance(Integer attendanceId,Attendance attendance,Principal user) throws AttendanceException, EmployeeException;

	Attendance deleteAttendance(Integer attendanceId,Principal user) throws AttendanceException, EmployeeException;
	
	
	Attendance regularize(Integer employeeId,LocalDate date,LocalTime fromTime,LocalTime toTime,Principal user) throws AttendanceException,EmployeeException;
	
	Attendance getAttendanceByAttendanceId(Integer attendanceId,Principal user) throws AttendanceException, EmployeeException;

	List<DayAttendanceDto> getAttendanceByMonthAndEmployeeId(Integer month, Integer year, Integer employeeId,Principal user)throws AttendanceException,EmployeeException, LeaveException;

	Attendance getLastAttendance(Integer employeeId, Principal user) throws AttendanceException, EmployeeException;

	
}
