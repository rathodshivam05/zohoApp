package com.clayfin.service;

import java.util.List;

import com.clayfin.dto.RegularizeDTO;
import com.clayfin.entity.RegularizationRequest;
import com.clayfin.enums.RegularizationStatus;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.RegularizationException;

public interface RegularizationService {

	RegularizationRequest addRegularizationRequest(RegularizeDTO request, Integer employeeId)
			throws RegularizationException, AttendanceException, EmployeeException;

	RegularizationRequest getRegularizationRequest(Integer regularizationReqeustId) throws RegularizationException;

	RegularizationRequest updateRegularizationStatusAndManagerId(Integer regularizationId, RegularizationStatus status,Integer managerId)
			throws RegularizationException;
	
	
	RegularizationRequest deleteRegularizationById(Integer regularizationId)
			throws RegularizationException;

	
	List<RegularizationRequest> getRegularizationRequestByEmployeeId(Integer employeeId)
			throws RegularizationException;

	List<RegularizationRequest> getRegularizationRequestByManagerId(Integer managerId)
			throws RegularizationException;

	List<RegularizationRequest> getRegularizationRequestByEmployeeIdAndStatus(Integer employeeId,RegularizationStatus status)
			throws RegularizationException;

	List<RegularizationRequest> getRegularizationRequestByManagerIdAndStatus(Integer managerId,RegularizationStatus status)
			throws RegularizationException;

}
