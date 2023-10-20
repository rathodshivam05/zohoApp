package com.clayfin.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clayfin.dto.EmployeeDto;
import com.clayfin.dto.GeneralResponse;
import com.clayfin.entity.Employee;
import com.clayfin.entity.EmployeeProfile;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.exception.TaskException;
import com.clayfin.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin
@RolesAllowed("ROLE_USER")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	

	@GetMapping("/hello")
	public String hello() {
		return "Hello WOrld ";
	}
	

	@PostMapping("/addEmployee/{hrId}/{managerId}")
	ResponseEntity<GeneralResponse> addEmployee(@RequestBody Employee employee,@PathVariable Integer hrId,@PathVariable Integer managerId,Principal user) throws EmployeeException {
		
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Employee Added");
		generalResponse.setData(employeeService.addEmployee(employee,hrId,managerId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@PutMapping("/updateEmployee/{employeeId}")
	ResponseEntity<GeneralResponse> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employee, Principal user)
			throws EmployeeException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Employee Updated");
		generalResponse.setData(employeeService.updateEmployee(employeeId, employee,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@PutMapping("/updateEmployeeProfileByEmployeeId/{employeeId}")
	ResponseEntity<GeneralResponse> updateEmployeeProfileByEmployeeId(@PathVariable Integer employeeId, @RequestBody EmployeeProfile employeeProfile,Principal user)
			throws EmployeeException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("EmployeeProfile Updated");
		generalResponse.setData(employeeService.updateEmployeeProfileByEmployeeId(employeeId,employeeProfile,user ));

		return ResponseEntity.ok(generalResponse);
	}
	
	@PutMapping("/updateManagerToEmployeeByHr/{hrId}/{employeeId}")
	ResponseEntity<GeneralResponse> setManagerToEmployeeByHr(@PathVariable Integer hrId,Integer employeeId,@RequestBody EmployeeDto employeeDto,Principal user) throws EmployeeException{
		
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Employee Manager Updated");
		generalResponse.setData(employeeService.setManagerToEmployeeByHr(employeeId, hrId,employeeDto,user));

		return ResponseEntity.ok(generalResponse);
	}


	@DeleteMapping("/deleteEmployee/{employeeId}/{hrId}")
	ResponseEntity<GeneralResponse> deleteEmployee(@PathVariable Integer employeeId,@PathVariable Integer hrId,Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Employee Deleted");
		generalResponse.setData(employeeService.deleteEmployee(employeeId,hrId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getEmployeeById/{employeeId}")
	ResponseEntity<GeneralResponse> getEmployeeById(@PathVariable Integer employeeId,Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Employee with Id :" + employeeId);
		generalResponse.setData(employeeService.getEmployeeById(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@GetMapping("/getAllBirthdayEmployees")
	ResponseEntity<GeneralResponse> getAllBirthdayEmployeesBy(Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Employees by Date :" + LocalDate.now());
		generalResponse.setData(employeeService.getAllBirthdayEmployeesBy(user));

		return ResponseEntity.ok(generalResponse);
	}
	
	
	
	@GetMapping("/getEmployeeProfileByEmployeeId/{employeeId}")
	ResponseEntity<GeneralResponse> getEmployeeProfileByEmployeeId(@PathVariable Integer employeeId,Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Employee with Id :" + employeeId);
		generalResponse.setData(employeeService.getEmployeeProfileByEmployeeId(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	

	@GetMapping("/getEmployeeByEmail/{email}")
	ResponseEntity<GeneralResponse> getEmployeeByEmail(@PathVariable String email,Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Employee with Email :" + email);
		generalResponse.setData(employeeService.getEmployeeByEmail(email,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@GetMapping("/getAllEmployees")
	@RolesAllowed("ROLE_USER")
	ResponseEntity<GeneralResponse> getAllEmployees(Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found All Employees ");
		generalResponse.setData(employeeService.getAllEmployees(user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getEmployeeManager/{employeeId}")
	ResponseEntity<GeneralResponse> getEmployeeManager(@PathVariable Integer employeeId,Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Employee Manager with Employee Id " + employeeId);
		generalResponse.setData(employeeService.getEmployeeManager(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getEmployeesOfManager/{managerId}")
	ResponseEntity<GeneralResponse> getEmployeesOfManager(@PathVariable Integer managerId,Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found All Employees of Manager with Manager Id " + managerId);
		generalResponse.setData(employeeService.getEmployeesOfManager(managerId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllTasks/{employeeId}")
	ResponseEntity<GeneralResponse> getAllTaskMyEmployeeId(@PathVariable Integer employeeId,Principal user)
			throws EmployeeException, TaskException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found All Tasks Of a Employee with Id " + employeeId);
		generalResponse.setData(employeeService.getAllTaskMyEmployeeId(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllLeaves/{employeeId}")
	ResponseEntity<GeneralResponse> getAllLeavesByEmployeeId(@PathVariable Integer employeeId,Principal user)
			throws EmployeeException, LeaveException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found All Leaves Of a Employee with Id " + employeeId);
		generalResponse.setData(employeeService.getAllLeavesByEmployeeId(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllAttendance/{employeeId}")
	ResponseEntity<GeneralResponse> getAllAttendanceByEmployeeId(@PathVariable Integer employeeId,Principal user)
			throws EmployeeException, AttendanceException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found All Attendances Of a Employee with Id " + employeeId);
		generalResponse.setData(employeeService.getAllAttendanceByEmployeeId(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@PutMapping("/updateEmployeeSkillSet/{employeeId}/{skills}")
	ResponseEntity<GeneralResponse> updateSkillSet(@PathVariable Integer employeeId,@PathVariable List<String> skills,Principal user) throws EmployeeException{
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("SkillSet Updated for the Employee with Id " + employeeId);
		generalResponse.setData(employeeService.updateSkillSet(employeeId, skills,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@PostMapping("/addEmployeeProfileData/{employeeId}")
	ResponseEntity<GeneralResponse> addEmployeeProfileData(@PathVariable Integer employeeId,@RequestBody EmployeeProfile employeeProfile,Principal user) throws EmployeeException{
		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("employee profile Updated for the Employee with Id " + employeeId);
		generalResponse.setData(employeeService.addEmployeeProfileData(employeeId, employeeProfile,user));

		return ResponseEntity.ok(generalResponse);
	}

}
