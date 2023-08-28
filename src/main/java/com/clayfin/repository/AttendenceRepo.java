package com.clayfin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clayfin.entity.Attendance;

public interface AttendenceRepo extends JpaRepository<Attendance, Integer> {


	List<Attendance> findByEmployeeEmployeeIdAndDate(Integer employeeId,LocalDate date);
	
	List<Attendance> findByEmployeeEmployeeId(Integer employeeId);
	
	Attendance findTopByEmployeeEmployeeIdOrderByEmployeeEmployeeIdDesc(Integer employeeId);
	
	Attendance findTopByEmployeeEmployeeIdOrderByCheckInTimestampDesc(Integer employeeId);
}
