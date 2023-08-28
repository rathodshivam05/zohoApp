package com.clayfin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clayfin.entity.RegularizationRequest;
import com.clayfin.enums.RegularizationStatus;

public interface RegularizationRepo extends JpaRepository<RegularizationRequest, Integer>{
	
	
	List<RegularizationRequest> findByEmployeeEmployeeId(Integer employeeId);
	
	List<RegularizationRequest> findByEmployeeManagerEmployeeId(Integer managerId);

	List<RegularizationRequest> findByEmployeeEmployeeIdAndStatus(Integer employeeId,RegularizationStatus status);
	
	List<RegularizationRequest> findByEmployeeManagerEmployeeIdAndStatus(Integer managerId,RegularizationStatus status);
	
	
	

}
