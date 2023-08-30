package com.clayfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clayfin.entity.EmployeeProfile;
import com.clayfin.exception.EmployeeException;

@Repository
public interface EmployeeProfileRepo extends JpaRepository<EmployeeProfile, Integer> {
	
	//public EmployeeProfile findByEmployeeEmployeeId(Integer employeeId) throws EmployeeException;

	public EmployeeProfile findByEmployeeEmployeeId(Integer employeeId);

	

}
