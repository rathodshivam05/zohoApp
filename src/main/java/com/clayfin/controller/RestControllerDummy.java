package com.clayfin.controller;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clayfin.dto.GeneralResponse;
import com.clayfin.exception.EmployeeException;
import com.clayfin.service.EmployeeService;

@RestController
public class RestControllerDummy {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/hi")
	public String hi() {
		return "<h1>Success</h1>";
	}
	
	@GetMapping("/getAllEmployees")
	@RolesAllowed("ROLE_USER")
	ResponseEntity<GeneralResponse> getAllEmployees(Principal user) throws EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found All Employees ");
		generalResponse.setData(employeeService.getAllEmployees(user));

		return ResponseEntity.ok(generalResponse);
	}

}
