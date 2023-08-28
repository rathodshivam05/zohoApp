package com.clayfin.service;

import java.time.LocalDate;
import java.util.List;

import com.clayfin.entity.Task;
import com.clayfin.enums.TaskStatus;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.TaskException;

public interface TaskService {

	Task assingTask(Task task, Integer managerId, Integer employeeId) throws TaskException,EmployeeException;

	Task updateTask(Integer taskId, Task task) throws TaskException,EmployeeException;

	Task deleteTask(Integer taskId) throws TaskException,EmployeeException;

	Task getTaskById(Integer taskId) throws TaskException,EmployeeException;

	List<Task> getAllTaskByEmployeeId(Integer employeeId) throws EmployeeException, TaskException;

	List<Task> getAllTaskAssignedByManagerId(Integer managerId) throws EmployeeException, TaskException;

	List<Task> getAllTaskByStatusAndEmployeeId(Integer employeeId, TaskStatus status)
			throws EmployeeException, TaskException;

	List<Task> getAllTaskByDateAndEmployeeId(Integer employeeId, LocalDate date)
			throws EmployeeException, TaskException;

	List<Task> getAllTaskByStatusAndManagerId(Integer managerId, TaskStatus status) throws EmployeeException, TaskException;

	List<Task> getAllTaskByDateAndManagerId(Integer managerId, LocalDate date) throws EmployeeException, TaskException;
	
	Task updateTaskStatus(Integer taskId,TaskStatus status)throws TaskException;
	

}
