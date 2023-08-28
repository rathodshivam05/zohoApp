package com.clayfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clayfin.entity.EmployeeProfile;

@Repository
public interface EmployeeProfileRepo extends JpaRepository<EmployeeProfile, Integer> {
	
	
	EmployeeProfile findByEmployeeEmployeeId(Integer employeeId);

}
