package com.clayfin.service;

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
	
	

	Employee addEmployee(Employee employee,Integer hrId) throws EmployeeException;

	Employee updateEmployee(Integer employeeId,Employee employee) throws EmployeeException;

	Employee deleteEmployee(Integer employeeId) throws EmployeeException;

	Employee getEmployeeById(Integer employeeId) throws EmployeeException;
	
	Employee getEmployeeByEmail(String email ) throws EmployeeException;
	
	
	List<Employee> getAllEmployees() throws EmployeeException;

	Employee getEmployeeManager(Integer employeeId) throws EmployeeException;

	List<Employee> getEmployeesOfManager(Integer managerId) throws EmployeeException;
	
	List<Task> getAllTaskMyEmployeeId(Integer employeeId) throws EmployeeException,TaskException;
	
	List<LeaveRecord> getAllLeavesByEmployeeId(Integer employeeId) throws EmployeeException,LeaveException;
	
	List<Attendance> getAllAttendanceByEmployeeId(Integer employeeId) throws EmployeeException,AttendanceException;

	Employee updateSkillSet(Integer employeeId,List<String> skills) throws EmployeeException;
	
	EmployeeProfile getEmployeeProfileByEmployeeId(Integer employeeId) throws EmployeeException;

	EmployeeProfile addEmployeeProfileData(Integer employeeId, EmployeeProfile employeeProfile) throws EmployeeException;

	Employee setManagerToEmployeeByHr(Integer employeeId,  Integer hrId,EmployeeDto employeeDto) throws EmployeeException;

	

}
