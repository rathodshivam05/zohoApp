package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;

public interface LeaveService {

	LeaveRecord applyLeave(LeaveRecord leaveRecord, Integer employeeId,Principal user) throws LeaveException, EmployeeException;

	LeaveRecord updateLeave(Integer leaveId, LeaveRecord leaveRecord,Principal user) throws LeaveException, EmployeeException;

	LeaveRecord deleteLeave(Integer leaveId,Principal user) throws LeaveException, EmployeeException;

	LeaveRecord getLeaveByLeaveId(Integer leaveId,Principal user) throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByEmployeeId(Integer employeeId,Principal user) throws EmployeeException, LeaveException;

	List<LeaveRecord> getAllLeavesByManagerId(Integer managerId,Principal user) throws EmployeeException, LeaveException;

	List<LeaveRecord> getLeavesByEmployeeIdAndStatus(Integer employeeId, LeaveStatus status,Principal user)
			throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByManagerIdAndStatus(Integer managerId, LeaveStatus status,Principal user)
			throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByEmployeeIdAndLeaveType(Integer employeeId, LeaveType leaveType,Principal user)
			throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByManagerIdAndLeaveType(Integer managerId, LeaveType leaveType,Principal user)
			throws LeaveException, EmployeeException;
	
	LeaveRecord updateLeaveStatus(Integer leaveId,LeaveStatus status,String response, Principal user) throws LeaveException, EmployeeException;

}
