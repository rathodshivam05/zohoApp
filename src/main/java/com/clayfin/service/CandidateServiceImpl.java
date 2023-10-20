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

import com.clayfin.dto.EmployeeDto;
import com.clayfin.entity.Candidate;
import com.clayfin.entity.Employee;
import com.clayfin.enums.RoleType;
import com.clayfin.exception.CandidateException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.repository.CandidateRepo;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepo candidateRepo;

	@Autowired
	private RepoHelper repoHelper;

	@Autowired
	private EmployeeRepo employeeRepo;
	

	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}

	@Override
	public Candidate addCandidate(Candidate candidate, Integer hrId, Principal user) throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(hrId)) {

			if (candidate == null)
				throw new CandidateException();
			Employee employee = employeeRepo.getById(hrId);
			if (employee.getRole().equals(RoleType.ROLE_HR)) {
				candidate.setCreatedAt(LocalDate.now());
				return candidateRepo.save(candidate);
			} else {
				throw new CandidateException(Constants.NOT_VALID_HR);
			}

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public Candidate updateCandidate(Integer candidateId, Candidate Candidate, Principal user)
			throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(candidateId)) {

			Candidate Candidate1 = getCandidateById(candidateId,user);
			if (repoHelper.isCandidateExist(candidateId)) {
				BeanUtils.copyProperties(Candidate, Candidate1, getNullPropertyNames(Candidate));
				return candidateRepo.save(Candidate1);
			} else
				throw new CandidateException(Constants.CANDIDATE_NOT_FOUND_WITH_ID + candidateId);

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
	public Candidate getCandidateById(Integer candidateId, Principal user) throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(candidateId)) {

			return candidateRepo.findById(candidateId)
					.orElseThrow(() -> new CandidateException(Constants.CANDIDATE_NOT_FOUND_WITH_ID + candidateId));

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public Candidate deleteCandidate(Integer candidateId, Principal user) throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(candidateId)) {

			Candidate Candidate = getCandidateById(candidateId,user);
			candidateRepo.deleteById(candidateId);
			return Candidate;

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public Candidate getCandidateByEmail(String email, Principal user) throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmail().equals(email)) {

			return candidateRepo.findByEmail(email)
					.orElseThrow(() -> new CandidateException(Constants.CANDIDATE_NOT_FOUND_WITH_ID + email));

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public List<Candidate> getAllCandidates(Principal user) throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (
				repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {

			List<Candidate> Candidates = candidateRepo.findAll();

			if (Candidates.isEmpty())
				throw new CandidateException(Constants.CANDIDATE_NOT_FOUND_WITH_ID);
			return Candidates;

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

//		@Override
//		public Employee addEmployeeByCandidate(Candidate candidate,EmployeeDto employeeDto, Integer hrId) throws CandidateException, EmployeeException {
//			if (candidate == null)
//				throw new CandidateException();
//			Employee employee = employeeRepo.getById(hrId);
//			if(employee.getRole().equals(RoleType.ROLE_HR)) {
//				Employee employee1 = new Employee();
//				String name = candidate.getFirstName().strip().toLowerCase();
//				employee1.setUsername(candidate.getFirstName()+" "+candidate.getLastName());
//				employee1.setEmail(name+"@clayfin.com");
//				employee1.setPassword(candidate.getMobileNumber().toString());
//				employee1.setReportingTo(employeeRepo.getById(employeeDto.getManagerId()).getUsername());
//				employee1.setTitle(employeeDto.getTitle());
//				employee1.setManager(employeeRepo.getById(employeeDto.getManagerId()));
//				return employeeRepo.save(employee1);
//			}
//			else {
//				throw new EmployeeException(Constants.NOT_VALID_HR);
//			}
//		}

	@Override
	public Employee addEmployeeByCandidateId(Integer candidateId, EmployeeDto employeeDto, Integer hrId, Principal user)
			throws CandidateException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(hrId)) {

			Candidate candidate = candidateRepo.getById(candidateId);
			if (candidate == null)
				throw new CandidateException();
			Employee employee = employeeRepo.getById(hrId);
			if (employee.getRole().equals(RoleType.ROLE_HR)) {
				Employee employee1 = new Employee();
				String name = candidate.getFirstName().strip().toLowerCase();
				employee1.setUsername(candidate.getFirstName() + " " + candidate.getLastName());
				employee1.setEmail(name + "@clayfin.com");
				employee1.setJoiningDate(LocalDate.now());
				employee1.setRole(employeeDto.getRole());
				employee1.setManager(employeeRepo.getById(employeeDto.getManagerId()));
				employee1.setPassword(candidate.getMobileNumber().toString());
				employee1.setTitle(employeeDto.getTitle());
				employee1.setReportingTo(employeeRepo.getById(employeeDto.getManagerId()).getUsername());
				return employeeRepo.save(employee1);
			} else {
				throw new EmployeeException(Constants.NOT_VALID_HR);
			}

		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

}
