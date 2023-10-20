package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import com.clayfin.entity.Task;
import com.clayfin.enums.TaskStatus;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.TaskException;

public interface TaskService {

	Task assingTask(Task task, Integer managerId, Integer employeeId,Principal user) throws TaskException,EmployeeException;

	Task updateTask(Integer taskId, Task task,Principal user) throws TaskException,EmployeeException;

	Task deleteTask(Integer taskId,Principal user) throws TaskException,EmployeeException;

	Task getTaskById(Integer taskId,Principal user) throws TaskException,EmployeeException;

	List<Task> getAllTaskByEmployeeId(Integer employeeId,Principal user) throws EmployeeException, TaskException;

	List<Task> getAllTaskAssignedByManagerId(Integer managerId,Principal user) throws EmployeeException, TaskException;

	List<Task> getAllTaskByStatusAndEmployeeId(Integer employeeId, TaskStatus status,Principal user)
			throws EmployeeException, TaskException;

	List<Task> getAllTaskByDateAndEmployeeId(Integer employeeId, LocalDate date,Principal user)
			throws EmployeeException, TaskException;

	List<Task> getAllTaskByStatusAndManagerId(Integer managerId, TaskStatus status,Principal user) throws EmployeeException, TaskException;

	List<Task> getAllTaskByDateAndManagerId(Integer managerId, LocalDate date,Principal user) throws EmployeeException, TaskException;
	
	Task updateTaskStatus(Integer taskId,TaskStatus status,Principal user)throws TaskException, EmployeeException;
	

}
