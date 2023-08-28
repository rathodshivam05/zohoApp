package com.clayfin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clayfin.entity.Employee;
import com.clayfin.exception.EmployeeException;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	
	public Optional<Employee> findByEmail(String email) throws EmployeeException;
	
	public Optional<Employee> findByManagerEmployeeId(Integer employeeId) throws EmployeeException;

	
}
