package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Employee;
import com.clayfin.entity.LeaveTracker;
import com.clayfin.enums.ProbationStatus;
import com.clayfin.exception.EmployeeException;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.LeaveTrackerRepo;
import com.clayfin.utility.Constants;

@Service
public class LeaveTrackerService {
	
	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	private LeaveTrackerRepo leaveTrackerRepo;
	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}

	@Scheduled(cron = "0 0 1 1 * ?") 
	public void assignPrivilegeLeaves() {
		List<LeaveTracker> leaveTrackers = leaveTrackerRepo.findAll();
		for (LeaveTracker leaveTrackerForEmployee : leaveTrackers ) {
			if(leaveTrackerForEmployee.getEmployee().getProbationStatus().equals(ProbationStatus.PROBATION)) {
				leaveTrackerForEmployee.setAvailablePrivilegeLeave(leaveTrackerForEmployee.getAvailablePrivilegeLeave() + 1);
			}else if(leaveTrackerForEmployee.getEmployee().getProbationStatus().equals(ProbationStatus.PERMANENT)) {
				leaveTrackerForEmployee.setAvailablePrivilegeLeave(leaveTrackerForEmployee.getAvailablePrivilegeLeave() + 2);
			}
			leaveTrackerRepo.save(leaveTrackerForEmployee);
		}
	}
	
	
	public LeaveTracker getByEmployeeId(Integer employeeId,Principal user) throws EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {

		return leaveTrackerRepo.findByEmployeeEmployeeId(employeeId);
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
		
	}
	
	
	
	
}
