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

import com.clayfin.dto.EmployeeDto;
import com.clayfin.dto.GeneralResponse;
import com.clayfin.entity.Candidate;
import com.clayfin.exception.CandidateException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.service.CandidateService;

@RestController
@RequestMapping("/hr")
@CrossOrigin
public class HrController {
	
	@Autowired
	private CandidateService candidateService;

	

	@PostMapping("/addcandidate/{hrId}")
	ResponseEntity<GeneralResponse> addcandidate(@PathVariable Integer hrId,@RequestBody Candidate candidate,Principal user) throws CandidateException, EmployeeException {
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("candidate Added");
		generalResponse.setData(candidateService.addCandidate(candidate,hrId,user));
		return ResponseEntity.ok(generalResponse);
	}
	

	@PostMapping("/addEmployeeByCandidateId/{hrId}/{candidateId}")
	ResponseEntity<GeneralResponse> addEmployeeByCandidateId(@PathVariable Integer hrId,@RequestBody EmployeeDto employeeDto, @PathVariable Integer candidateId,Principal user) throws CandidateException, EmployeeException {
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("candidate Added");
		generalResponse.setData(candidateService.addEmployeeByCandidateId(candidateId,employeeDto,hrId,user));
		return ResponseEntity.ok(generalResponse);
	}
	
//	
//	@PostMapping("/addEmployeeByCandidate/{hrId}")
//	ResponseEntity<GeneralResponse> addEmployeeByCandidate(@PathVariable Integer hrId,@RequestBody EmployeeDto employeeDto,@RequestBody Candidate candidate) throws CandidateException, EmployeeException {
//		var generalResponse = new GeneralResponse();
//		generalResponse.setMessage("Employee Added");
//		generalResponse.setData(candidateService.addEmployeeByCandidate(candidate,employeeDto,hrId));
//		return ResponseEntity.ok(generalResponse);
//	}
//	
	
	@PutMapping("/updatecandidate/{candidateId}")
	ResponseEntity<GeneralResponse> updatecandidate(@PathVariable Integer candidateId, @RequestBody Candidate candidate,Principal user)
			throws CandidateException, EmployeeException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("candidate Updated");
		generalResponse.setData(candidateService.updateCandidate(candidateId, candidate,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	


	@DeleteMapping("/deletecandidate/{candidateId}")
	ResponseEntity<GeneralResponse> deletecandidate(@PathVariable Integer candidateId,Principal user) throws CandidateException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("candidate Deleted");
		generalResponse.setData(candidateService.deleteCandidate(candidateId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getcandidateById/{candidateId}")
	ResponseEntity<GeneralResponse> getcandidateById(@PathVariable Integer candidateId,Principal user) throws CandidateException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found candidate with Id :" + candidateId);
		generalResponse.setData(candidateService.getCandidateById(candidateId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	
	
	
	

	@GetMapping("/getcandidateByEmail/{email}")
	ResponseEntity<GeneralResponse> getcandidateByEmail(@PathVariable String email,Principal user) throws CandidateException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found candidate with Email :" + email);
		generalResponse.setData(candidateService.getCandidateByEmail(email,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getAllcandidates")
	ResponseEntity<GeneralResponse> getAllcandidates(Principal user) throws CandidateException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found All candidates ");
		generalResponse.setData(candidateService.getAllCandidates(user));

		return ResponseEntity.ok(generalResponse);
	}

	

}
