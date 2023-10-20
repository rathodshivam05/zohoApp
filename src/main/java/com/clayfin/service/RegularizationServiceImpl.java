package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.dto.RegularizeDTO;
import com.clayfin.entity.Attendance;
import com.clayfin.entity.Employee;
import com.clayfin.entity.RegularizationRequest;
import com.clayfin.enums.RegularizationStatus;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.RegularizationException;
import com.clayfin.repository.AttendenceRepo;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.RegularizationRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

@Service
public class RegularizationServiceImpl implements RegularizationService {

	@Autowired
	private RegularizationRepo regularizationRepo;

	@Autowired
	private AttendenceRepo attendanceRepo;

	@Autowired
	private RepoHelper repoHelper;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	

	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}

	@Override
	public RegularizationRequest addRegularizationRequest(RegularizeDTO request, Integer employeeId,Principal user)
			throws AttendanceException, EmployeeException, RegularizationException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {

		System.out.println(request);
		Boolean isValid = repoHelper.isValidRegularizationRequest(request, employeeId);
		
		if (!isValid)
			throw new RegularizationException("Not A valid Regularization Request ");
//
//		if (request.getDate().getDayOfYear() != request.getToTime().getDayOfYear()
//				|| request.getDate().getDayOfYear() != request.getToTime().getDayOfYear())
//			throw new RegularizationException("Invalid Dates Provided ");
//		
		
		
		
		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(()->new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID+employeeId));

		var regularizeRequest = new RegularizationRequest();

		regularizeRequest.setCreatedTimestamp(LocalDateTime.now());
		regularizeRequest.setStatus(RegularizationStatus.PENDING);
		regularizeRequest.setCheckInTimestamp(request.getFromTime());
		regularizeRequest.setCheckOutTimestamp(request.getToTime());
		regularizeRequest.setDate(request.getDate());
		regularizeRequest.setEmployee(employee);

		return regularizationRepo.save(regularizeRequest);

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public RegularizationRequest getRegularizationRequest(Integer regularizationReqeustId,Principal user)
			throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {

		return regularizationRepo.findById(regularizationReqeustId).orElseThrow(() -> new RegularizationException(
				"Regularization Request Not Found with Regularization Id " + regularizationReqeustId));

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public RegularizationRequest updateRegularizationStatusAndManagerId(Integer regularizationId,
			RegularizationStatus status, Integer managerId,Principal user) throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {

		RegularizationRequest request = regularizationRepo.findById(regularizationId)
				.orElseThrow(() -> new RegularizationException(
						"Regularization Request Not Found with Regularization Id " + regularizationId));

		if (request.getEmployee() != null && request.getEmployee().getManager() != null
				&& !request.getEmployee().getManager().getEmployeeId().equals(managerId))
			throw new RegularizationException(Constants.NOT_VALID_MANAGER);

		if (request.getStatus() != RegularizationStatus.PENDING)
			throw new RegularizationException("Regularization Request Status Already In " + status);

		request.setStatus(status);

		if (status.equals(RegularizationStatus.ACCEPTED)) {

			Attendance attendance = new Attendance();
			attendance.setCheckInTimestamp(request.getCheckInTimestamp());
			attendance.setCheckOutTimestamp(request.getCheckOutTimestamp());
			attendance.setDate(request.getDate());
			attendance.setEmployee(request.getEmployee());

			LocalTime spentTime = repoHelper.findTimeBetweenTimestamps(request.getCheckInTimestamp(),
					request.getCheckOutTimestamp());

			attendance.setSpentHours(spentTime);

			attendanceRepo.save(attendance);

		}

		return regularizationRepo.save(request);

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public List<RegularizationRequest> getRegularizationRequestByEmployeeId(Integer employeeId,Principal user)
			throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {

		List<RegularizationRequest> requests = regularizationRepo.findByEmployeeEmployeeId(employeeId);

		if (requests.isEmpty())
			throw new RegularizationException(Constants.REGULARIZATION_REQUEST_NOT_FOUND);

		return requests;

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public List<RegularizationRequest> getRegularizationRequestByManagerId(Integer managerId,Principal user)
			throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {

		if (!repoHelper.isValidManager(managerId))
			throw new RegularizationException(Constants.NOT_VALID_MANAGER);

		List<RegularizationRequest> requests = regularizationRepo.findByEmployeeManagerEmployeeId(managerId);

		if (requests.isEmpty())
			throw new RegularizationException(Constants.REGULARIZATION_REQUEST_NOT_FOUND);

		return requests;

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public List<RegularizationRequest> getRegularizationRequestByEmployeeIdAndStatus(Integer employeeId,
			RegularizationStatus status,Principal user) throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {

		List<RegularizationRequest> requests = regularizationRepo.findByEmployeeEmployeeIdAndStatus(employeeId, status);

		if (requests.isEmpty())
			throw new RegularizationException(Constants.REGULARIZATION_REQUEST_NOT_FOUND);

		return requests;

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public List<RegularizationRequest> getRegularizationRequestByManagerIdAndStatus(Integer managerId,
			RegularizationStatus status,Principal user) throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {

		if (!repoHelper.isValidManager(managerId))
			throw new RegularizationException(Constants.NOT_VALID_MANAGER);

		List<RegularizationRequest> requests = regularizationRepo.findByEmployeeManagerEmployeeIdAndStatus(managerId,
				status);

		if (requests.isEmpty())
			throw new RegularizationException(Constants.REGULARIZATION_REQUEST_NOT_FOUND);

		return requests;

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public RegularizationRequest deleteRegularizationById(Integer regularizationId,Principal user)
			throws RegularizationException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {
RegularizationRequest request = getRegularizationRequest(regularizationId,user);

		regularizationRepo.delete(request);

		return request;

}
else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

}
