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
import com.clayfin.dto.RegularizeDTO;
import com.clayfin.entity.Attendance;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@PostMapping("/checkIn/{employeeId}")
	ResponseEntity<GeneralResponse> checkInAttendance(@PathVariable Integer employeeId)
			throws EmployeeException, AttendanceException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("CheckedIn Success with employee Id : " + employeeId);
		generalResponse.setData(attendanceService.checkInAttendance(employeeId));

		return ResponseEntity.ok(generalResponse);
	}

	@PostMapping("/checkOut/{employeeId}")
	ResponseEntity<GeneralResponse> checkOutAttendance(@PathVariable Integer employeeId)
			throws EmployeeException, AttendanceException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("CheckedOut Success with employee Id " + employeeId);
		generalResponse.setData(attendanceService.checkOutAttendance(employeeId));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAttendanceByDate/{date}/{employeeId}")
	ResponseEntity<GeneralResponse> getAttendanceByDateAndEmployeeId(@PathVariable LocalDate date,
			@PathVariable Integer employeeId) throws EmployeeException, AttendanceException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Attendance Found By Date and Employee Id : " + date + " " + employeeId);
		generalResponse.setData(attendanceService.getAttendanceByDateAndEmployeeId(date, employeeId));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAttendance/{employeeId}")
	ResponseEntity<GeneralResponse> getAttendanceByEmployeeId(@PathVariable Integer employeeId)
			throws AttendanceException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Attendance Found By Employee Id" + employeeId);
		generalResponse.setData(attendanceService.getAttendanceByEmployeeId(employeeId));

		return ResponseEntity.ok(generalResponse);
	}

	@PutMapping("/updateAttendance/{attendanceId}")
	ResponseEntity<GeneralResponse> updateAttendance(@PathVariable Integer attendanceId,
			@RequestBody Attendance attendance) throws AttendanceException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Attendance Update Success" + attendanceId);
		generalResponse.setData(attendanceService.updateAttendance(attendanceId, attendance));

		return ResponseEntity.ok(generalResponse);
	}

	@DeleteMapping("/deleteAttendance/{attendanceId}")
	ResponseEntity<GeneralResponse> deleteAttendance(@PathVariable Integer attendanceId) throws AttendanceException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Attendance Delete Success" + attendanceId);
		generalResponse.setData(attendanceService.deleteAttendance(attendanceId));
		return ResponseEntity.ok(generalResponse);
	}

	@PutMapping("/reguralize/{employeeId}")
	ResponseEntity<GeneralResponse> regularize(Integer employeeId, @RequestBody RegularizeDTO reguralizeDTO)
			throws AttendanceException, EmployeeException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Reguralization Success with Id " + employeeId);
		generalResponse.setData(attendanceService.regularize(employeeId, reguralizeDTO.getDate(),
				reguralizeDTO.getFromTime(), reguralizeDTO.getToTime()));

		return ResponseEntity.ok(generalResponse);
	}

}
