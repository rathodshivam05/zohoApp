package com.clayfin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clayfin.entity.EmployeeProfile;
import com.clayfin.exception.EmployeeException;

@Repository
public interface EmployeeProfileRepo extends JpaRepository<EmployeeProfile, Integer> {
	
	//public EmployeeProfile findByEmployeeEmployeeId(Integer employeeId) throws EmployeeException;

	public EmployeeProfile findByEmployeeEmployeeId(Integer employeeId) throws EmployeeException;


	public EmployeeProfile findByOtherEmail(String email);

	public List<EmployeeProfile> findByBirthDate(LocalDate date);



}
