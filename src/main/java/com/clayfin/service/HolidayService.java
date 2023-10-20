package com.clayfin.service;

import java.security.Principal;
import java.util.List;

import com.clayfin.entity.Holidays;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.HolidayException;

public interface HolidayService {
	
	 Holidays addHoliday(Integer hrId, Holidays holiday,Principal user) throws EmployeeException, HolidayException;
	 


		Holidays updateHoliday(Integer hrId,Integer HolidayId,Holidays Holiday,Principal user) throws HolidayException, EmployeeException;

		Holidays deleteHoliday(Integer HolidayId, Integer hrId,Principal user) throws HolidayException, EmployeeException;

		Holidays getHolidayById(Integer HolidayId,Principal user) throws HolidayException, EmployeeException;
		
		
		
		List<Holidays> getAllHolidays(Principal user) throws HolidayException, EmployeeException;



}
