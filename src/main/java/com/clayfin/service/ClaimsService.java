package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import com.clayfin.entity.Claims;
import com.clayfin.exception.ClaimException;
import com.clayfin.exception.EmployeeException;

public interface ClaimsService {
	
	Claims addClaim(Claims claims, Integer employeeId, Principal user) throws EmployeeException;
	
	Claims deleteClaim(Integer claimId, Principal user) throws EmployeeException, ClaimException;
	
	Claims updateClaim(Integer claimId, Claims claim, Principal user) throws EmployeeException, ClaimException;
	
	Claims updateStausClaimByAdmin(Integer claimId,Integer adminId,Double amount,String status, Principal user) throws EmployeeException, ClaimException;

	Claims getByClaimId(Integer claimId,Principal user) throws EmployeeException, ClaimException;
	
	List<Claims> getAllClaimsByStatus(Integer adminId,String status,Principal user) throws EmployeeException,ClaimException;

	List<Claims> getAllClaimsByEmployeeId(Integer employeeId, Principal user) throws EmployeeException, ClaimException; 
}
