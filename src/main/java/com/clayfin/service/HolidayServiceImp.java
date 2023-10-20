package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Employee;
import com.clayfin.entity.Holidays;
import com.clayfin.enums.RoleType;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.HolidayException;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.HolidayRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

@Service
public class HolidayServiceImp implements HolidayService {

	@Autowired
	private RepoHelper repoHelper;

	@Autowired
	private HolidayRepo holidayRepo;

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	

	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}

	@Override
	public Holidays addHoliday(Integer hrId, Holidays holiday, Principal user)
			throws EmployeeException, HolidayException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(hrId)) {

			if (repoHelper.isEmployeeExist(hrId)) {
				Employee hr = employeeService.getEmployeeById(hrId, user);
				if (hr.getRole().equals(RoleType.ROLE_HR)) {

					holiday.setTimeStamp(LocalDateTime.now());
					if (repoHelper.isHolidayExistByDate(holiday)) {
						throw new HolidayException(Constants.HOLIDAY_EXIST_ONSAME_DATE + holiday.getDateOfHoliday());
					}
					return holidayRepo.save(holiday);
				}
				throw new EmployeeException(Constants.NOT_VALID_HR);

			}

			throw new EmployeeException(Constants.Hr_NOT_FOUND_WITH_ID + hrId);
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
	public Holidays updateHoliday(Integer hrId, Integer holidayId, Holidays holiday, Principal user)
			throws HolidayException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(hrId)) {

			if (repoHelper.isEmployeeExist(hrId)) {
				Employee hr = employeeService.getEmployeeById(hrId, user);
				if (hr.getRole().equals(RoleType.ROLE_HR)) {
					if (repoHelper.isHolidayExistById(holidayId)) {
						Holidays holidayNew = getHolidayById(holidayId,user);
						if (holidayNew != null) {
							BeanUtils.copyProperties(holiday, holidayNew, getNullPropertyNames(holiday));
							return holidayRepo.save(holidayNew);
						}

					} else {
						throw new HolidayException(Constants.HOLIDAY_NOT_FOUND + holidayId);
					}

				}
				throw new EmployeeException(Constants.NOT_VALID_HR);

			}

			throw new EmployeeException(Constants.Hr_NOT_FOUND_WITH_ID + hrId);
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

	@Override
	public Holidays getHolidayById(Integer HolidayId, Principal user) throws HolidayException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {

			if (repoHelper.isHolidayExistById(HolidayId)) {
				return holidayRepo.findById(HolidayId).get();
			}
			throw new HolidayException(Constants.HOLIDAY_NOT_FOUND + HolidayId);
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}

	}

	@Override
	public List<Holidays> getAllHolidays(Principal user) throws HolidayException, EmployeeException {
		Employee currentUser = userinfo(user);

	if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {


		return holidayRepo.findAll();
	}else

	{
		throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
	}
	}

	@Override
	public Holidays deleteHoliday(Integer HolidayId, Integer hrId, Principal user)
			throws HolidayException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(hrId)) {

			if (repoHelper.isEmployeeExist(hrId)) {
				Employee hr = employeeService.getEmployeeById(hrId, user);
				if (hr.getRole().equals(RoleType.ROLE_HR)) {
					if (repoHelper.isHolidayExistById(HolidayId)) {
						Holidays holidays = holidayRepo.getById(HolidayId);
						holidayRepo.deleteById(HolidayId);
						return holidays;
					}
					throw new HolidayException(Constants.HOLIDAY_NOT_FOUND + HolidayId);
				}
				throw new EmployeeException(Constants.NOT_VALID_HR);

			}

			throw new EmployeeException(Constants.Hr_NOT_FOUND_WITH_ID + hrId);
		} else {
			throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
		}
	}

}
