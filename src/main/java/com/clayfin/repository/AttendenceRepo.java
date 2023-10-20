package com.clayfin.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clayfin.entity.Attendance;

public interface AttendenceRepo extends JpaRepository<Attendance, Integer> {


	List<Attendance> findByEmployeeEmployeeIdAndDate(Integer employeeId,LocalDate date);
	
	List<Attendance> findByEmployeeEmployeeId(Integer employeeId);
//
//	@Query("SELECT i FROM attendance i WHERE (i.check_out_timestamp IS NULL OR i.check_out_timestamp = '') AND i.date < :fromDate ")
//	List<Attendance> findAllMissedCheckoutAttendances(LocalDate fromDate);
//	
	
	
	List<Attendance> findAllAttendancesByDate(LocalDate date);
	
	Attendance findTopByEmployeeEmployeeIdOrderByEmployeeEmployeeIdDesc(Integer id);
	
	//Attendance findBottomByEmployeeEmployeeIdOrderByEmployeeEmployeeId(Integer id);
	
	
	Attendance findTopByEmployeeEmployeeIdOrderByCheckInTimestampDesc(Integer employeeId);

	Attendance save(Optional<Attendance> attendance1);
	
	
}
