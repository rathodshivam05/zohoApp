package com.clayfin.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clayfin.entity.Employee;
import com.clayfin.exception.EmployeeException;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	
	public Optional<Employee> findByEmail(String email) throws EmployeeException;
	
	public Optional<Employee> findByManagerEmployeeId(Integer employeeId) throws EmployeeException;
	
	 
	public List<Employee> findByJoiningDate(LocalDate date);
	
	
	public List<Employee> findFirst10ByOrderByEmployeeIdDesc();

	public  Employee findByUsername(String username) ;

	
	}
	
	
	

