package com.clayfin.controller;

import java.security.Principal;

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

import com.clayfin.dto.GeneralResponse;
import com.clayfin.entity.Claims;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.ClaimException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.service.ClaimsService;

@RestController
@CrossOrigin
@RequestMapping("/claims")
public class ClaimsController {
	

	@Autowired
	private ClaimsService claimsService;
	
	
	@GetMapping("/getByClaimId/{claimId}")
	ResponseEntity<GeneralResponse> getByClaimId(@PathVariable Integer claimId,Principal user) throws EmployeeException,ClaimException{
		var generalResponse = new GeneralResponse();
		
		generalResponse.setMessage("Claim found with Id : " + claimId);
		generalResponse.setData(claimsService.getByClaimId(claimId  ,user));
		return ResponseEntity.ok(generalResponse);
	}
	
	
	@PostMapping("/addClaim/{employeeId}")
	ResponseEntity<GeneralResponse> addClaim(@RequestBody Claims claims,@PathVariable Integer employeeId,Principal user) throws EmployeeException {
		
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Claim Added");
		generalResponse.setData(claimsService.addClaim(claims,employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	
	
	
	@DeleteMapping("/deleteClaim/{attendanceId}")
	ResponseEntity<GeneralResponse> deleteClaim(@PathVariable Integer claimId,Principal user) throws  EmployeeException, ClaimException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Claim Delete Success" + claimId);
		generalResponse.setData(claimsService.deleteClaim(claimId,user));
		return ResponseEntity.ok(generalResponse);
	}
	
	
	@PutMapping("/updateClaim/{claimId}")
	ResponseEntity<GeneralResponse> updateClaim(@PathVariable Integer claimId,@RequestBody Claims claim, Principal user) throws  EmployeeException, ClaimException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Claim Update Success" + claimId);
		generalResponse.setData(claimsService.updateClaim( claimId, claim,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	
	@PutMapping("/updateStausClaimByAdmin/{claimId}/{adminId}")
	ResponseEntity<GeneralResponse> updateStausClaimByAdmin(@PathVariable Integer claimId,@PathVariable Integer adminId,@RequestBody Double amount,@RequestBody String status, Principal user) throws  EmployeeException, ClaimException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Claim Status Update Success" + claimId);
		generalResponse.setData(claimsService.updateStausClaimByAdmin( claimId,adminId,amount, status,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@GetMapping("/getAllClaimsByEmployeeId/{employeeId}")
	ResponseEntity<GeneralResponse> getAllClaimsByEmployeeId(@PathVariable Integer employeeId,Principal user)
			throws EmployeeException, ClaimException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Found All Claims Of a Employee with Id " + employeeId);
		generalResponse.setData(claimsService.getAllClaimsByEmployeeId(employeeId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	
	
	@GetMapping("/getAllClaimsByStatus")
	ResponseEntity<GeneralResponse> getAllClaimsByStatus(@PathVariable Integer adminId,@RequestBody String status, Principal user) throws EmployeeException, ClaimException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found All Claims ");
		generalResponse.setData(claimsService.getAllClaimsByStatus(adminId,status,user));

		return ResponseEntity.ok(generalResponse);
	}
	

}
