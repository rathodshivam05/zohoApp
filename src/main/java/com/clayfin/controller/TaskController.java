package com.clayfin.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clayfin.dto.GeneralResponse;
import com.clayfin.entity.Task;
import com.clayfin.enums.TaskStatus;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.TaskException;
import com.clayfin.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/addTask/{managerId}/{employeeId}")
	ResponseEntity<GeneralResponse> assingTask(@RequestBody Task task, @PathVariable Integer managerId,
			@PathVariable Integer employeeId) throws TaskException, EmployeeException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.assingTask(task, managerId, employeeId));
		generalResponse.setMessage("Task Assigned successfully to id: " + employeeId + "from id: " + managerId);
		return ResponseEntity.ok(generalResponse);
	}

	@PutMapping("/updateTask/{taskId}")
	ResponseEntity<GeneralResponse> updateTask(@PathVariable Integer taskId, @RequestBody Task task)
			throws TaskException, EmployeeException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getTaskById(taskId));
		generalResponse.setMessage("Task Found with id:" + taskId);
		return ResponseEntity.ok(generalResponse);
	}

	@DeleteMapping("/deleteTask/{taskId}")
	ResponseEntity<GeneralResponse> deleteTask(@PathVariable Integer taskId) throws TaskException, EmployeeException {
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Task Deleted sucessfully with Id " + taskId);
		generalResponse.setData(taskService.deleteTask(taskId));
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getTask/{taskId}")
	ResponseEntity<GeneralResponse> getTaskById(@PathVariable Integer taskId) throws TaskException, EmployeeException {
		var generalResponse = new GeneralResponse();
		generalResponse.setData(taskService.getTaskById(taskId));
		generalResponse.setMessage("Task Found with id: " + taskId);
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTasksByEmployeeId/{employeeId}")
	ResponseEntity<GeneralResponse> getAllTaskByEmployeeId(@PathVariable Integer employeeId)
			throws EmployeeException, TaskException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getAllTaskByEmployeeId(employeeId));
		generalResponse.setMessage("All Tasks Found with Employee id: " + employeeId);
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTaskAssingedByManager/{managerId}")
	ResponseEntity<GeneralResponse> getAllTaskAssignedByManagerId(@PathVariable Integer managerId)
			throws EmployeeException, TaskException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getAllTaskAssignedByManagerId(managerId));
		generalResponse.setMessage("Tasks Found with Manager  id: " + managerId);
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTaskByEmployeeAndStatus/{employeeId}/{status}")
	ResponseEntity<GeneralResponse> getAllTaskByStatusAndEmployeeId(@PathVariable Integer employeeId,
			@PathVariable TaskStatus status) throws EmployeeException, TaskException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getAllTaskByStatusAndEmployeeId(employeeId, status));
		generalResponse.setMessage("Tasks Found with employee id: " + employeeId + " with status :" + status);
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTaskByEmployeeAndDate/{employeeId}/{date}")
	ResponseEntity<GeneralResponse> getAllTaskByDateAndEmployeeId(@PathVariable Integer employeeId,
			@PathVariable LocalDate date) throws EmployeeException, TaskException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getAllTaskByDateAndEmployeeId(employeeId, date));
		generalResponse.setMessage("Tasks Found with employee id: " + employeeId + " with date: " + date);
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTaskByManagerAndStatus/{managerId}/{status}")
	ResponseEntity<GeneralResponse> getAllTaskByStatusAndManagerId(@PathVariable Integer managerId,
			@PathVariable TaskStatus status) throws EmployeeException, TaskException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getAllTaskByStatusAndManagerId(managerId, status));
		generalResponse.setMessage("Tasks Found with Manager id:" + managerId + " with status: " + status);
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTaskByManagerAndDate/{managerId}/{date}")
	ResponseEntity<GeneralResponse> getAllTaskByDateAndManagerId(@PathVariable Integer managerId,
			@PathVariable LocalDate date) throws EmployeeException, TaskException {
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.getAllTaskByDateAndManagerId(managerId, date));
		generalResponse.setMessage("Tasks Found with Manager id:" + managerId + " with date: " + date);
		return ResponseEntity.ok(generalResponse);
	}
	
	
	@PutMapping("/updateTaskStatus/{taskId}/{status}")
	ResponseEntity<GeneralResponse> updateTaskStatus(@PathVariable Integer taskId,@PathVariable TaskStatus status)throws TaskException{
		var generalResponse = new GeneralResponse();

		generalResponse.setData(taskService.updateTaskStatus(taskId, status));
		generalResponse.setMessage("Task Updated to "+status);
		return ResponseEntity.ok(generalResponse);
	}
}