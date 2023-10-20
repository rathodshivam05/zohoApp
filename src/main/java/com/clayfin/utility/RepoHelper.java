package com.clayfin.utility;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clayfin.dto.DayAttendanceDto;
import com.clayfin.dto.HolidayDto;
import com.clayfin.dto.RegularizeDTO;
import com.clayfin.entity.Attendance;
import com.clayfin.entity.Employee;
import com.clayfin.entity.Holidays;
import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.AttendanceStatus;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.RoleType;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.exception.RegularizationException;
import com.clayfin.repository.AttendenceRepo;
import com.clayfin.repository.CandidateRepo;
import com.clayfin.repository.ClaimsRepo;
import com.clayfin.repository.EmployeeProfileRepo;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.HolidayRepo;
import com.clayfin.repository.LeaveRepo;
import com.clayfin.repository.TaskRepo;
import com.clayfin.service.AttendanceService;

import net.bytebuddy.asm.Advice.Local;

@Component
public class RepoHelper {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private LeaveRepo leaveRepo;

	@Autowired
	private TaskRepo taskRepo;

	@Autowired
	private EmployeeProfileRepo employeeProfileRepo;

	@Autowired
	private CandidateRepo candidateRepo;

	@Autowired
	private AttendenceRepo attendanceRepo;

	@Autowired
	private HolidayRepo holidayRepo;

	@Autowired
	private ClaimsRepo claimsRepo;
	
	

	public Boolean isEmployeeExist(Integer employeeId) {
		return employeeRepo.findById(employeeId).isPresent();
	}

	public Boolean isHolidayExistById(Integer holidayId) {
		return holidayRepo.findById(holidayId).isPresent();
	}

	public Boolean isClaimExistById(Integer claimId) {
		return claimsRepo.findById(claimId).isPresent();
	}

	public Boolean isHolidayExistByDate(Holidays holiday) {
		List<Holidays> holidays = holidayRepo.findAll();
		if (holidays.size() != 0) {
			for (Holidays holidayOld : holidays) {
				if (holiday.getDateOfHoliday().equals(holidayOld.getDateOfHoliday())) {
					return true;
				}
			}
		}
		return false;

	}

	public Boolean isCandidateExist(Integer candidateId) {
		return candidateRepo.findById(candidateId).isPresent();
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

	public LocalTime findTimeBetweenTimestamps(LocalTime localTime, LocalTime localTime2) {

		Duration duration = Duration.between(localTime, localTime2);

		int hours = (int) duration.toHoursPart();
		int minutes = (int) duration.toMinutesPart();
		int seconds = (int) duration.toSecondsPart();

		return LocalTime.of(hours, minutes, seconds);
	}

	public boolean isValidRegularizationRequest(RegularizeDTO dto, Integer employeeId)
			throws RegularizationException, AttendanceException, EmployeeException {

		try {
			LocalTime fromTime = dto.getFromTime();
			LocalTime toTime = dto.getToTime();

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

			List<Attendance> alreadyPresentAttendance = attendanceRepo.findByEmployeeEmployeeIdAndDate(employeeId,
					date);

			System.out.println(alreadyPresentAttendance.size() + "  attendances Found ");

			for (int i = 0; i < alreadyPresentAttendance.size() - 2; i++) {
				if (alreadyPresentAttendance.get(i).getCheckOutTimestamp().isAfter(fromTime)
						&& alreadyPresentAttendance.get(i + 1).getCheckInTimestamp().isAfter(toTime))
					return true;
			}

			Integer lastAttendanceIndex = alreadyPresentAttendance.size() - 1;

			LocalTime lastCheckOutTime = alreadyPresentAttendance.get(lastAttendanceIndex).getCheckOutTimestamp();

			if (lastCheckOutTime != null && lastCheckOutTime.isBefore(fromTime))
				return true;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public Boolean isValidManager(Integer managerId) {

		try {

			Employee employee = employeeRepo.findById(managerId)
					.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + managerId));

			if (!employee.getRole().equals(RoleType.ROLE_MANAGER))
				return false;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public Integer getDaysInMonth(Integer month, Integer year) {
		Integer days = null;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			days = 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = 30;
		} else {
			if (month == 2) {
				if (year % 400 == 0 && year % 100 == 0) {
					days = 29;
				} else {
					if (year % 4 == 0 && year % 100 != 0) {
						days = 29;
					} else
						days = 28;
				}
			}

		}

		return days;
	}

	@SuppressWarnings("null")
	public DayAttendanceDto getTotalTimeInDay(LocalDate date, Integer employeeId,Principal user)
			throws EmployeeException, LeaveException, AttendanceException {
		DayAttendanceDto attendanceDto = new DayAttendanceDto();
		List<Attendance> attendances = attendanceRepo.findByEmployeeEmployeeIdAndDate(employeeId, date);
		List<Holidays> holidays = holidayRepo.findAll();
		// changes

		if (attendances.size() == 0) {
			String dayOfWeek = date.getDayOfWeek().toString();

			for (Holidays i : holidays) {
				if (i.getDateOfHoliday().equals(date)) {
					attendanceDto.setStatus(i.getHolidays());
				}

				else if (dayOfWeek.equals("SUNDAY") || dayOfWeek.equals("SATURDAY")) {
					attendanceDto.setStatus("HOLIDAY");
				} else {

					List<LeaveRecord> leaves = leaveRepo.findByEmployeeEmployeeId(employeeId);

					for (LeaveRecord leave : leaves) {
						if ((date.isAfter(leave.getStartDate()) && date.isBefore(leave.getEndDate())
								|| (date.isEqual(leave.getStartDate()) || date.isEqual(leave.getEndDate())))
								&& leave.getStatus().equals(LeaveStatus.APPROVED)) {
							attendanceDto.setStatus("LEAVE");

						} else {
							if (date.isBefore(LocalDate.now())) {
								attendanceDto.setStatus("ABSENT");
							} else {
								attendanceDto.setStatus("NULL");
							}

						}
					}

				}
				
			}
			attendanceDto.setDate(date);
			attendanceDto.setTotalHoursInADay(null);
			return attendanceDto;

		} else {

			Integer hours = 0;
			Integer minutes = 0;
			Integer seconds = 0;
			
		//	Attendance lastAttendance = attendanceService.getLastAttendance(employeeId, user);
			
			
		
			for (Attendance attendance : attendances) {
			//	LocalTime time = attendance.getSpentHours();
				LocalTime startTime = attendance.getCheckInTimestamp();
				LocalTime endTime;
				if(attendance.getCheckOutTimestamp()==null) {
					endTime = LocalTime.now();
				}
				else {
					endTime = attendance.getCheckOutTimestamp();
				}
				
				LocalTime time = findTimeBetweenTimestamps(startTime, endTime);
				
				hours += time.getHour();
				minutes += time.getMinute();
				seconds += time.getSecond();
			}
			attendanceDto.setDate(date);
			LocalTime totalTime = LocalTime.of(hours, minutes, seconds);
			attendanceDto.setTotalHoursInADay(totalTime);
			if (date.isEqual(LocalDate.now())) {
				attendanceDto.setStatus("COUNTING");
			} else {
				attendanceDto.setStatus("PRESENT");
			}
			return attendanceDto;
			}
		

	}

}
