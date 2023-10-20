package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import com.clayfin.dto.RegularizeDTO;
import com.clayfin.entity.RegularizationRequest;
import com.clayfin.enums.RegularizationStatus;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.RegularizationException;

public interface RegularizationService {

	RegularizationRequest addRegularizationRequest(RegularizeDTO request, Integer employeeId,Principal user)
			throws RegularizationException, AttendanceException, EmployeeException;

	RegularizationRequest getRegularizationRequest(Integer regularizationReqeustId,Principal user) throws RegularizationException, EmployeeException;

	RegularizationRequest updateRegularizationStatusAndManagerId(Integer regularizationId, RegularizationStatus status,Integer managerId,Principal user)
			throws RegularizationException, EmployeeException;
	
	
	RegularizationRequest deleteRegularizationById(Integer regularizationId,Principal user)
			throws RegularizationException, EmployeeException;

	
	List<RegularizationRequest> getRegularizationRequestByEmployeeId(Integer employeeId,Principal user)
			throws RegularizationException, EmployeeException;

	List<RegularizationRequest> getRegularizationRequestByManagerId(Integer managerId,Principal user)
			throws RegularizationException, EmployeeException;

	List<RegularizationRequest> getRegularizationRequestByEmployeeIdAndStatus(Integer employeeId,RegularizationStatus status,Principal user)
			throws RegularizationException, EmployeeException;

	List<RegularizationRequest> getRegularizationRequestByManagerIdAndStatus(Integer managerId,RegularizationStatus status,Principal user)
			throws RegularizationException, EmployeeException;

}
