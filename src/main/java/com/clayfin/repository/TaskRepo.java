package com.clayfin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.clayfin.entity.Task;
import com.clayfin.enums.TaskStatus;

public interface TaskRepo extends CrudRepository<Task, Integer> {

	List<Task> findByEmployeeEmployeeId(Integer employeeId);
	
	List<Task> findByEmployeeManagerEmployeeId(Integer managerId);
	
	List<Task> findByEmployeeEmployeeIdAndStatus(Integer employeeId,TaskStatus status);

	List<Task> findByEmployeeManagerEmployeeIdAndStatus(Integer managerId,TaskStatus status);
	
	//native query.
	//List<Task> getAllTaskByEmployeeIdAndDate(Integer employeeId,LocalDateTime startDate,LocalDateTime endDate);
}
