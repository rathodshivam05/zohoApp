package com.clayfin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clayfin.entity.Claims;
import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.LeaveStatus;

public interface ClaimsRepo extends JpaRepository<Claims, Integer>{
	
	
	List<Claims> findByEmployeeEmployeeIdAndStatus(Integer employeeId, String status);
	
	List<Claims> findByEmployeeEmployeeId(Integer employeeId);

}
