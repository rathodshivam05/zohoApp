package com.clayfin.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clayfin.dto.RegularizeDTO;
import com.clayfin.entity.Attendance;
import com.clayfin.entity.Employee;
import com.clayfin.enums.RoleType;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.RegularizationException;
import com.clayfin.repository.AttendenceRepo;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.LeaveRepo;
import com.clayfin.repository.TaskRepo;

@Component
public class RepoHelper {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private LeaveRepo leaveRepo;

	@Autowired
	private TaskRepo taskRepo;

	@Autowired
	private AttendenceRepo attendanceRepo;
	


	public Boolean isEmployeeExist(Integer employeeId) {
		return employeeRepo.findById(employeeId).isPresent();
	}
	
	

	public Boolean isLeaveExist(Integer leaveId) {
		return leaveRepo.findById(leaveId).isPresent();
	}

	public Boolean isTaskExist(Integer taskId) {
		return taskRepo.findById(taskId).isPresent();
	}

	public Boolean isValidEmployeeOfThisManager(Integer managerId, Integer employeeId) {
		Employee employee = employeeRepo.findById(employeeId).orElseGet(null);

		if (employee == null)
			return false;

		if (employee.getManager() != null && employee.getManager().getEmployeeId() == managerId)
			return true;

		return false;
	}

	public LocalTime findTimeBetweenTimestamps(LocalDateTime fromTime, LocalDateTime toTime) {

		Duration duration = Duration.between(fromTime, toTime);

		int hours = (int) duration.toHoursPart();
		int minutes = (int) duration.toMinutesPart();

		System.out.println("Hours :" + hours);
		System.out.println("Minutes : " + minutes);

		return LocalTime.of(hours, minutes);
	}

	public boolean isValidRegularizationRequest(RegularizeDTO dto, Integer employeeId)
			throws RegularizationException, AttendanceException, EmployeeException {

		try {
			LocalDateTime fromTime = dto.getFromTime();
			LocalDateTime toTime = dto.getToTime();

			LocalDate date = dto.getDate();

			int presentDayNumber = LocalDateTime.now().getDayOfYear();

			int applieadDayNumber = date.getDayOfYear();
			int daysDifference = Math.abs(Math.subtractExact(presentDayNumber, applieadDayNumber));
			if (daysDifference > 4)
				throw new RegularizationException("Regularization Request Is Acceptable Only for Past 4 days ");

			LocalTime spentHours = findTimeBetweenTimestamps(fromTime, toTime);

			if (!isEmployeeExist(employeeId))
				throw new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId);

			if (spentHours.getHour() > 2)
				throw new AttendanceException(Constants.NOT_REGURALIZABLE);

			List<Attendance> alreadyPresentAttendance = attendanceRepo.findByEmployeeEmployeeIdAndDate(employeeId,date);

			System.out.println(alreadyPresentAttendance.size() + "  attendances Found ");

			for (int i = 0; i < alreadyPresentAttendance.size() - 2; i++) {
				if (alreadyPresentAttendance.get(i).getCheckOutTimestamp().isBefore(fromTime)
						&& alreadyPresentAttendance.get(i + 1).getCheckInTimestamp().isAfter(toTime))
					return true;
			}

			Integer lastAttendanceIndex = alreadyPresentAttendance.size() - 1;

			LocalDateTime lastCheckOutTime = alreadyPresentAttendance.get(lastAttendanceIndex).getCheckOutTimestamp();

			if (lastCheckOutTime != null && lastCheckOutTime.isBefore(fromTime))
				return true;

		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	
	public Boolean isValidManager(Integer managerId) {
		
		try {
			
		Employee employee=	employeeRepo.findById(managerId)
			.orElseThrow(()->new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID+managerId));
		
		if(!employee.getRole().equals(RoleType.ROLE_MANAGER))
			return false;
			
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}

}
