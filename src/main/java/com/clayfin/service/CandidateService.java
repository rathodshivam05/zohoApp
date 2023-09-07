package com.clayfin.service;

import java.util.List;

import com.clayfin.dto.EmployeeDto;
import com.clayfin.entity.Candidate;
import com.clayfin.entity.Employee;
import com.clayfin.exception.CandidateException;
import com.clayfin.exception.EmployeeException;

public interface CandidateService {
	
	

	Candidate addCandidate(Candidate candidate,Integer hrId) throws CandidateException;

	Candidate updateCandidate(Integer candidateId,Candidate candidate) throws CandidateException;

	Candidate deleteCandidate(Integer candidateId) throws CandidateException;

	Candidate getCandidateById(Integer candidateId) throws CandidateException;
	
	Candidate getCandidateByEmail(String email ) throws CandidateException;
	
	List<Candidate> getAllCandidates() throws CandidateException;

	//Employee addEmployeeByCandidate(Candidate candidate,EmployeeDto employeeDto,Integer hrId) throws CandidateException, EmployeeException;

	Employee addEmployeeByCandidateId(Integer candidateId,EmployeeDto employeeDto, Integer hrId) throws CandidateException, EmployeeException;
	
	
}
