package com.clayfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clayfin.entity.LeaveTracker;

@Repository
public interface LeaveTrackerRepo extends JpaRepository<LeaveTracker, Integer> {
	
	LeaveTracker findByEmployeeEmployeeId(Integer employeeId);
}
