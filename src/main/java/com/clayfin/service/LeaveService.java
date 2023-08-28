package com.clayfin.service;

import java.util.List;

import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;

public interface LeaveService {

	LeaveRecord applyLeave(LeaveRecord leaveRecord, Integer employeeId) throws LeaveException, EmployeeException;

	LeaveRecord updateLeave(Integer leaveId, LeaveRecord leaveRecord) throws LeaveException;

	LeaveRecord deleteLeave(Integer leaveId) throws LeaveException;

	LeaveRecord getLeaveByLeaveId(Integer leaveId) throws LeaveException;

	List<LeaveRecord> getLeavesByEmployeeId(Integer employeeId) throws EmployeeException, LeaveException;

	List<LeaveRecord> getAllLeavesByManagerId(Integer managerId) throws EmployeeException, LeaveException;

	List<LeaveRecord> getLeavesByEmployeeIdAndStatus(Integer employeeId, LeaveStatus status)
			throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByManagerIdAndStatus(Integer managerId, LeaveStatus status)
			throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByEmployeeIdAndLeaveType(Integer employeeId, LeaveType leaveType)
			throws LeaveException, EmployeeException;

	List<LeaveRecord> getLeavesByManagerIdAndLeaveType(Integer managerId, LeaveType leaveType)
			throws LeaveException, EmployeeException;
	
	LeaveRecord updateLeaveStatus(Integer leaveId,LeaveStatus status) throws LeaveException;

}
