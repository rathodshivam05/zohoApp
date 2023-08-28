package com.clayfin.controller;

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
import com.clayfin.entity.LeaveRecord;
import com.clayfin.enums.LeaveStatus;
import com.clayfin.enums.LeaveType;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.service.LeaveService;

@RestController
@RequestMapping("/leave")
public class LeaveController {

	@Autowired
	private LeaveService leaveService;

	@PostMapping("/applyLeave/{employeeId}")
	ResponseEntity<GeneralResponse> applyLeave(@RequestBody LeaveRecord leaveRecord, @PathVariable Integer employeeId)
			throws LeaveException, EmployeeException {
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Leave Applied");
		generalResponse.setData(leaveService.applyLeave(leaveRecord, employeeId));

		return ResponseEntity.ok(generalResponse);
	}

	@PutMapping("/updateLeave/{leaveId}")
	ResponseEntity<GeneralResponse> updateLeave(@PathVariable Integer leaveId, @RequestBody LeaveRecord leaveRecord)
			throws LeaveException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Leave Updated");
		generalResponse.setData(leaveService.updateLeave(leaveId, leaveRecord));

		return ResponseEntity.ok(generalResponse);
	}

	@DeleteMapping("/deleteLeave/{leaveId}")
	ResponseEntity<GeneralResponse> deleteLeave(@PathVariable Integer leaveId) throws LeaveException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Leave Deleted");
		generalResponse.setData(leaveService.deleteLeave(leaveId));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getLeave/{leaveId}")
	ResponseEntity<GeneralResponse> getLeaveByLeaveId(@PathVariable Integer leaveId) throws LeaveException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found Leave By Id " + leaveId);
		generalResponse.setData(leaveService.getLeaveByLeaveId(leaveId));
		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getLeavesByEmployeeId/{employeeId}")
	ResponseEntity<GeneralResponse> getLeavesByEmployeeId(@PathVariable Integer employeeId)
			throws EmployeeException, LeaveException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found Leaves By Employee Id  " + employeeId);
		generalResponse.setData(leaveService.getLeavesByEmployeeId(employeeId));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllLeavesByManagerId/{managerId}")
	ResponseEntity<GeneralResponse> getAllLeavesByManagerId(@PathVariable Integer managerId)
			throws EmployeeException, LeaveException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found Leaves By Manager Id " + managerId);
		generalResponse.setData(leaveService.getAllLeavesByManagerId(managerId));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getLeavesByEmployeeIdAndStatus/{employeeId}/{status}")
	ResponseEntity<GeneralResponse> getLeavesByEmployeeIdAndStatus(@PathVariable Integer employeeId,
			@PathVariable LeaveStatus status) throws LeaveException, EmployeeException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found Leaves By Employee Id and Status " + employeeId + " " + status);
		generalResponse.setData(leaveService.getLeavesByEmployeeIdAndStatus(employeeId, status));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getLeavesByManagerIdAndStatus/{managerId}/{status}")
	ResponseEntity<GeneralResponse> getLeavesByManagerIdAndStatus(@PathVariable Integer managerId,
			@PathVariable LeaveStatus status) throws LeaveException, EmployeeException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found Leaves By ManagerId and Status " + managerId + " " + status);
		generalResponse.setData(leaveService.getLeavesByManagerIdAndStatus(managerId, status));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getLeavesByEmployeeIdAndLeaveType/{employeeId}/{leaveType}")
	ResponseEntity<GeneralResponse> getLeavesByEmployeeIdAndLeaveType(@PathVariable Integer employeeId,
			@PathVariable LeaveType leaveType) throws LeaveException, EmployeeException {
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found Leaves By Employee Id and Leave Type " + employeeId + " " + leaveType);
		generalResponse.setData(leaveService.getLeavesByEmployeeIdAndLeaveType(employeeId, leaveType));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getLeavesByManagerIdAndLeaveType/{managerId}/{leaveType}")
	ResponseEntity<GeneralResponse> getLeavesByManagerIdAndLeaveType(@PathVariable Integer managerId,
			@PathVariable LeaveType leaveType) throws LeaveException, EmployeeException {
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Leaves By Manager Id and Leave Type " + managerId + " " + leaveType);
		generalResponse.setData(leaveService.getLeavesByManagerIdAndLeaveType(managerId, leaveType));

		return ResponseEntity.ok(generalResponse);
	}
	
	@PutMapping("/updateLeaveStatus/{leaveId}/{status}")
	ResponseEntity<GeneralResponse> updateLeaveStatus(@PathVariable Integer leaveId,@PathVariable LeaveStatus status) throws LeaveException{
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Updated Leave Status to  " +status);
		generalResponse.setData(leaveService.updateLeaveStatus(leaveId, status));

		return ResponseEntity.ok(generalResponse);
	}

}
