package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Claims;
import com.clayfin.entity.Employee;
import com.clayfin.enums.RoleType;
import com.clayfin.exception.ClaimException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.repository.ClaimsRepo;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

@Service
public class ClaimsServiceImp implements ClaimsService {

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	ClaimsRepo claimsRepo;

	@Autowired
	RepoHelper repoHelper;

	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}

	@Override
	public Claims addClaim(Claims claims, Integer employeeId, Principal user) throws EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {
			claims.setEmployee(currentUser);
			claims.setStatus("pending");
			claims.setAddedTime(LocalDate.now());

			return claimsRepo.save(claims);
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public Claims deleteClaim(Integer claimId, Principal user) throws ClaimException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {
			if (repoHelper.isClaimExistById(claimId)) {
				Claims claims = getByClaimId(claimId, user);
				claimsRepo.deleteById(claimId);
				return claims;
			} else {
				throw new ClaimException(Constants.CLAIM_NOT_FOUND + claimId);
			}
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	private String[] getNullPropertyNames(Object source) {

		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);

	}

	@Override
	public Claims updateClaim(Integer claimId, Claims claim, Principal user) throws EmployeeException, ClaimException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {
			Claims claim1 = claimsRepo.getById(claimId);
			if (repoHelper.isClaimExistById(claimId)) {

				BeanUtils.copyProperties(claim, claim1, getNullPropertyNames(claim));
				return claimsRepo.save(claim1);
			} else {
				throw new ClaimException(Constants.CLAIM_NOT_FOUND + claimId);
			}
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public Claims updateStausClaimByAdmin(Integer claimId, Integer adminId, Double amount, String status,
			Principal user) throws EmployeeException, ClaimException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(adminId)) {
			Claims claim1 = claimsRepo.getById(claimId);
			if (repoHelper.isClaimExistById(claimId)) {
				Employee admin = employeeRepo.getById(adminId);
				if (admin.getRole().equals(RoleType.ROLE_ADMIN)) {
					claim1.setStatus(status);
					claim1.setAmountPaid(amount);
					return claimsRepo.save(claim1);
				} else {
					throw new EmployeeException(Constants.NOT_A_ADMIN);
				}

			} else {
				throw new ClaimException(Constants.CLAIM_NOT_FOUND + claimId);
			}
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public Claims getByClaimId(Integer claimId, Principal user) throws EmployeeException, ClaimException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {
			if (repoHelper.isClaimExistById(claimId)) {
				Claims claims = claimsRepo.findById(claimId).get();
				// claimsRepo.deleteById(claimId);
				return claims;
			} else {
				throw new ClaimException(Constants.CLAIM_NOT_FOUND + claimId);
			}
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public List<Claims> getAllClaimsByStatus(Integer adminId, String status, Principal user)
			throws EmployeeException, ClaimException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(adminId)) {

			Employee admin = employeeRepo.getById(adminId);
			if (admin.getRole().equals(RoleType.ROLE_ADMIN)) {
				List<Claims> claims = claimsRepo.findByEmployeeEmployeeIdAndStatus(adminId, status);
				// claimsRepo.deleteById(claimId);
				return claims;
			} else {
				throw new EmployeeException(Constants.NOT_A_ADMIN);
			}

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public List<Claims> getAllClaimsByEmployeeId(Integer employeeId, Principal user)
			throws EmployeeException, ClaimException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {

			Employee employee = employeeRepo.getById(employeeId);
			
				List<Claims> claims = claimsRepo.findByEmployeeEmployeeId(employeeId);
				
				return claims;
			} 

		 else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

}
