package com.clayfin.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Employee;
import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.LeaveRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveRepo leaveRepo;

	@Autowired
	private RepoHelper repoHelper;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public LeaveRecord applyLeave(LeaveRecord leaveRecord, Integer employeeId)
			throws LeaveException, EmployeeException {

		if (leaveRecord.getEndDate().isBefore(leaveRecord.getStartDate()))
			throw new LeaveException("End Date must be greater than start Date ");

		int days = (leaveRecord.getEndDate().getDayOfYear() - leaveRecord.getStartDate().getDayOfYear()) + 1;

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));

		leaveRecord.setDays(days);
		leaveRecord.setEmployee(employee);
		leaveRecord.setStatus(LeaveStatus.PENDING);
		leaveRecord.setCreatedTimestamp(LocalDateTime.now());
		return leaveRepo.save(leaveRecord);
	}

	@Override
	public LeaveRecord updateLeave(Integer leaveId, LeaveRecord leaveRecord) throws LeaveException {

		if (!repoHelper.isLeaveExist(leaveId))
			throw new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_LEAVE_ID + leaveId);

		return leaveRepo.save(leaveRecord);
	}

	@Override
	public LeaveRecord getLeaveByLeaveId(Integer leaveId) throws LeaveException {
		return leaveRepo.findById(leaveId)
				.orElseThrow(() -> new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_LEAVE_ID + leaveId));

	}

	@Override
	public LeaveRecord deleteLeave(Integer leaveId) throws LeaveException {
		LeaveRecord leaveRecord = getLeaveByLeaveId(leaveId);

		leaveRepo.delete(leaveRecord);

		return leaveRecord;
	}

	@Override
	public List<LeaveRecord> getLeavesByEmployeeId(Integer employeeId) throws EmployeeException, LeaveException {
		if (!repoHelper.isEmployeeExist(employeeId))
			throw new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId);

		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeEmployeeId(employeeId);

		if (leaveRecords.isEmpty())
			throw new LeaveException(Constants.NO_LEAVES_FOUND);

		return leaveRecords;
	}

	@Override
	public List<LeaveRecord> getAllLeavesByManagerId(Integer managerId) throws EmployeeException, LeaveException {
		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeManagerEmployeeId(managerId);

		if (leaveRecords.isEmpty())
			throw new LeaveException(Constants.NO_LEAVES_FOUND);

		return leaveRecords;
	}

	@Override
	public List<LeaveRecord> getLeavesByEmployeeIdAndStatus(Integer employeeId, LeaveStatus status)
			throws LeaveException {

		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeEmployeeIdAndStatus(employeeId, status);

		if (leaveRecords.isEmpty())
			throw new LeaveException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + employeeId);

		return leaveRecords;

	}

	@Override
	public List<LeaveRecord> getLeavesByManagerIdAndStatus(Integer managerId, LeaveStatus status)
			throws EmployeeException {

		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeManagerEmployeeIdAndStatus(managerId, status);

		if (leaveRecords.isEmpty())
			throw new EmployeeException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + managerId);

		return leaveRecords;
	}

	@Override
	public List<LeaveRecord> getLeavesByEmployeeIdAndLeaveType(Integer employeeId, LeaveType leaveType)
			throws EmployeeException {

		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);

		if (leaveRecords.isEmpty())
			throw new EmployeeException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + employeeId);

		return leaveRecords;
	}

	@Override
	public List<LeaveRecord> getLeavesByManagerIdAndLeaveType(Integer managerId, LeaveType leaveType)
			throws EmployeeException {
		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeManagerEmployeeIdAndLeaveType(managerId, leaveType);

		if (leaveRecords.isEmpty())
			throw new EmployeeException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + managerId);

		return leaveRecords;
	}

	@Override
	public LeaveRecord updateLeaveStatus(Integer leaveId, LeaveStatus status) throws LeaveException {
		LeaveRecord leave = leaveRepo.findById(leaveId)
				.orElseThrow(() -> new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_LEAVE_ID + leaveId));

		
		if(leave.getStatus()!=LeaveStatus.PENDING)
			throw new LeaveException("Leave Already Updated to "+leave.getStatus());
		
		leave.setStatus(status);

		return leaveRepo.save(leave);

	}

}
