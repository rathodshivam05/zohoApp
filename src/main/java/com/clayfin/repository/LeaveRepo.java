package com.clayfin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;

public interface LeaveRepo extends JpaRepository<LeaveRecord, Integer> {

	List<LeaveRecord> findByEmployeeEmployeeId(Integer employeeId);

	List<LeaveRecord> findByEmployeeEmployeeIdAndCreatedTimestamp(Integer employeeId, LocalDate timestamp);

	List<LeaveRecord> findByEmployeeEmployeeIdAndStatus(Integer employeeId, LeaveStatus status);

	List<LeaveRecord> findByEmployeeEmployeeIdAndLeaveType(Integer employeeId, LeaveType leaveType);

	List<LeaveRecord> findByEmployeeManagerEmployeeId(Integer managerId);

	List<LeaveRecord> findByEmployeeManagerEmployeeIdAndStatus(Integer managerId, LeaveStatus status);

	List<LeaveRecord> findByEmployeeManagerEmployeeIdAndLeaveType(Integer managerId, LeaveType leaveType);

}