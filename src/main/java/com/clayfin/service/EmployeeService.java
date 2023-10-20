package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import com.clayfin.dto.EmployeeDto;
import com.clayfin.entity.Attendance;
import com.clayfin.entity.Employee;
import com.clayfin.entity.EmployeeProfile;
import com.clayfin.entity.LeaveRecord;
import com.clayfin.entity.Task;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.exception.TaskException;

public interface EmployeeService {
	
	

	Employee addEmployee(Employee employee,Integer hrId,Integer managerId,Principal user) throws EmployeeException;

	Employee updateEmployee(Integer employeeId,Employee employee,Principal user) throws EmployeeException;

	Employee deleteEmployee(Integer employeeId,Integer hrId,Principal user) throws EmployeeException;

	Employee getEmployeeById(Integer employeeId,Principal user) throws EmployeeException;
	
	Employee getEmployeeByEmail(String email ,Principal user) throws EmployeeException;
	
	
	List<Employee> getAllEmployees(Principal user) throws EmployeeException;

	Employee getEmployeeManager(Integer employeeId,Principal user) throws EmployeeException;

	List<Employee> getEmployeesOfManager(Integer managerId,Principal user) throws EmployeeException;
	
	List<Task> getAllTaskMyEmployeeId(Integer employeeId,Principal user) throws EmployeeException,TaskException;
	
	List<LeaveRecord> getAllLeavesByEmployeeId(Integer employeeId,Principal user) throws EmployeeException,LeaveException;
	
	List<Attendance> getAllAttendanceByEmployeeId(Integer employeeId,Principal user) throws EmployeeException,AttendanceException;

	Employee updateSkillSet(Integer employeeId,List<String> skills,Principal user) throws EmployeeException;
	
	EmployeeProfile updateEmployeeProfileByEmployeeId(Integer employeeId,EmployeeProfile employeeProfile,Principal user) throws EmployeeException;
	
	
	EmployeeProfile getEmployeeProfileByEmployeeId(Integer employeeId,Principal user) throws EmployeeException;

	EmployeeProfile addEmployeeProfileData(Integer employeeId, EmployeeProfile employeeProfile,Principal user) throws EmployeeException;

	Employee setManagerToEmployeeByHr(Integer employeeId,  Integer hrId,EmployeeDto employeeDto,Principal user) throws EmployeeException;
	
	List<Employee> getAllBirthdayEmployeesBy(Principal user) throws EmployeeException;
	
	List<Employee> getAllNewEmployees(Principal user) throws EmployeeException;
}
