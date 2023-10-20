package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Employee;
import com.clayfin.entity.LeaveRecord;
import com.clayfin.entity.LeaveTracker;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.LeaveRepo;
import com.clayfin.repository.LeaveTrackerRepo;
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
	
	@Autowired
	private LeaveTrackerRepo leaveTrackerRepo;
	
	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}

	@Override
	public LeaveRecord applyLeave(LeaveRecord leaveRecord, Integer employeeId,Principal user)
			throws LeaveException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {




		if (leaveRecord.getEndDate().isBefore(leaveRecord.getStartDate()))
			throw new LeaveException("End Date must be greater than start Date ");
		
		LeaveTracker leaveTracker = leaveTrackerRepo.findByEmployeeEmployeeId(employeeId);
		
		int days = (leaveRecord.getEndDate().getDayOfYear() - leaveRecord.getStartDate().getDayOfYear()) ;

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
		if(leaveRecord.getLeaveType().equals(LeaveType.PRIVILEGE)) {
			if(leaveTracker.getAvailablePrivilegeLeave()> days || leaveTracker.getAvailablePrivilegeLeave()== days ) {
				leaveRecord.setDays(days);
				leaveRecord.setEmployee(employee);
				leaveRecord.setStatus(LeaveStatus.PENDING);
				leaveRecord.setCreatedTimestamp(LocalDateTime.now());
				
				leaveRecord.setManagerId(employee.getManager().getEmployeeId());
				

				LeaveTracker leaveTrackerNew  = leaveTrackerRepo.findById(leaveTracker.getId()).get();
				
				leaveTrackerNew.setBookedPrivilegeLeave(days);
				leaveTrackerNew.setAvailablePrivilegeLeave(leaveTracker.getAvailablePrivilegeLeave()-days);
				BeanUtils.copyProperties(leaveTracker, leaveTrackerNew, getNullPropertyNames(leaveTracker));
				leaveTrackerRepo.save(leaveTrackerNew);
				return leaveRepo.save(leaveRecord);
			}
			else {
				throw new LeaveException("Privilege Leaves not suffient ");
			}
		}
		
		leaveRecord.setDays(days);
		leaveRecord.setEmployee(employee);
		leaveRecord.setStatus(LeaveStatus.PENDING);
		leaveRecord.setCreatedTimestamp(LocalDateTime.now());
		leaveRecord.setManagerId(employee.getManager().getEmployeeId());
		
		return leaveRepo.save(leaveRecord);
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
		
	}

	@Override
	public LeaveRecord updateLeave(Integer leaveId, LeaveRecord leaveRecord,Principal user) throws LeaveException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {



		if (!repoHelper.isLeaveExist(leaveId))
			throw new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_LEAVE_ID + leaveId);

		LeaveRecord leaveRecord1 = getLeaveByLeaveId(leaveId,user);

		BeanUtils.copyProperties(leaveRecord, leaveRecord1, getNullPropertyNames(leaveRecord));
		return leaveRepo.save(leaveRecord1);}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	private String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	@Override
	public LeaveRecord getLeaveByLeaveId(Integer leaveId,Principal user) throws LeaveException, EmployeeException {
		
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {



		return leaveRepo.findById(leaveId)
				.orElseThrow(() -> new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_LEAVE_ID + leaveId));
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}

	}

	@Override
	public LeaveRecord deleteLeave(Integer leaveId,Principal user) throws LeaveException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {


LeaveRecord leaveRecord = getLeaveByLeaveId(leaveId,user);

		leaveRepo.delete(leaveRecord);

		return leaveRecord;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<LeaveRecord> getLeavesByEmployeeId(Integer employeeId,Principal user) throws EmployeeException, LeaveException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {


if (!repoHelper.isEmployeeExist(employeeId))
			throw new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId);

		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeEmployeeId(employeeId);

		if (leaveRecords.isEmpty())
			throw new LeaveException(Constants.NO_LEAVES_FOUND);

		return leaveRecords;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<LeaveRecord> getAllLeavesByManagerId(Integer managerId,Principal user) throws EmployeeException, LeaveException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {


List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeManagerEmployeeId(managerId);

		if (leaveRecords.isEmpty())
			throw new LeaveException(Constants.NO_LEAVES_FOUND);

		return leaveRecords;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<LeaveRecord> getLeavesByEmployeeIdAndStatus(Integer employeeId, LeaveStatus status,Principal user)
			throws LeaveException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {



		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeEmployeeIdAndStatus(employeeId, status);

		if (leaveRecords.isEmpty())
			throw new LeaveException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + employeeId);

		return leaveRecords;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}

	}

	@Override
	public List<LeaveRecord> getLeavesByManagerIdAndStatus(Integer managerId, LeaveStatus status,Principal user)
			throws EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {



		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeManagerEmployeeIdAndStatus(managerId, status);

		if (leaveRecords.isEmpty())
			throw new EmployeeException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + managerId);

		return leaveRecords;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<LeaveRecord> getLeavesByEmployeeIdAndLeaveType(Integer employeeId, LeaveType leaveType,Principal user)
			throws EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {



		List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);

		if (leaveRecords.isEmpty())
			throw new EmployeeException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + employeeId);

		return leaveRecords;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<LeaveRecord> getLeavesByManagerIdAndLeaveType(Integer managerId, LeaveType leaveType,Principal user)
			throws EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {


List<LeaveRecord> leaveRecords = leaveRepo.findByEmployeeManagerEmployeeIdAndLeaveType(managerId, leaveType);

		if (leaveRecords.isEmpty())
			throw new EmployeeException(Constants.NO_LEAVES_FOUND_WITH_EMPLOYEE_ID + managerId);

		return leaveRecords;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public LeaveRecord updateLeaveStatus(Integer leaveId, LeaveStatus status,String response,Principal user) throws LeaveException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {


LeaveRecord leave = leaveRepo.findById(leaveId)
				.orElseThrow(() -> new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_LEAVE_ID + leaveId));

		if (leave.getStatus() != LeaveStatus.PENDING)
			throw new LeaveException("Leave Already Updated to " + leave.getStatus());

		leave.setStatus(status);
		leave.setResponse(response);
		return leaveRepo.save(leave);
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}

	}

}
