package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import com.clayfin.dto.EmployeeDto;
import com.clayfin.entity.Candidate;
import com.clayfin.entity.Employee;
import com.clayfin.exception.CandidateException;
import com.clayfin.exception.EmployeeException;

public interface CandidateService {
	
	

	Candidate addCandidate(Candidate candidate,Integer hrId,Principal user) throws CandidateException, EmployeeException;

	Candidate updateCandidate(Integer candidateId,Candidate candidate,Principal user) throws CandidateException, EmployeeException;

	Candidate deleteCandidate(Integer candidateId,Principal user) throws CandidateException, EmployeeException;

	Candidate getCandidateById(Integer candidateId,Principal user) throws CandidateException, EmployeeException;
	
	Candidate getCandidateByEmail(String email ,Principal user) throws CandidateException, EmployeeException;
	
	List<Candidate> getAllCandidates(Principal user) throws CandidateException, EmployeeException;

	//Employee addEmployeeByCandidate(Candidate candidate,EmployeeDto employeeDto,Integer hrId) throws CandidateException, EmployeeException;

	Employee addEmployeeByCandidateId(Integer candidateId,EmployeeDto employeeDto, Integer hrId,Principal user) throws CandidateException, EmployeeException;
	
	
}
