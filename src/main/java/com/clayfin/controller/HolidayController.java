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
import com.clayfin.entity.Holidays;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.HolidayException;
import com.clayfin.service.HolidayService;

@RestController
@RequestMapping("/holiday")
@CrossOrigin
public class HolidayController {
	
	
	@Autowired
	HolidayService holidayService;
	
	@PostMapping("/addHoliday/{hrId}")
	ResponseEntity<GeneralResponse> addHoliday(@RequestBody Holidays holiday,@PathVariable Integer hrId,Principal user) throws HolidayException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Holiday Added");
		generalResponse.setData(holidayService.addHoliday(hrId,holiday,user));

		return ResponseEntity.ok(generalResponse);
	}

	@PutMapping("/updateHoliday/{holidayId}/{hrId}")
	ResponseEntity<GeneralResponse> updateHoliday(@PathVariable Integer hrId,@PathVariable Integer holidayId, @RequestBody Holidays holiday,Principal user)
			throws HolidayException, EmployeeException {

		var generalResponse = new GeneralResponse();

		generalResponse.setMessage("Holiday Updated");
		generalResponse.setData(holidayService.updateHoliday(hrId,holidayId, holiday,user));

		return ResponseEntity.ok(generalResponse);
	}

	@DeleteMapping("/deleteHoliday/{holidayId}")
	ResponseEntity<GeneralResponse> deleteHoliday(@PathVariable Integer hrId,@PathVariable Integer holidayId,Principal user) throws HolidayException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Holiday Deleted");
		generalResponse.setData(holidayService.deleteHoliday(hrId,holidayId,user));

		return ResponseEntity.ok(generalResponse);
	}

	@GetMapping("/getHolidayById/{HolidayId}")
	ResponseEntity<GeneralResponse> getHolidayById(@PathVariable Integer HolidayId,Principal user) throws HolidayException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Holiday with Id :" + HolidayId);
		generalResponse.setData(holidayService.getHolidayById(HolidayId,user));

		return ResponseEntity.ok(generalResponse);
	}
	
	@GetMapping("/getAllBirthdayHolidays")
	ResponseEntity<GeneralResponse> getAllBirthdayHolidaysBy(Principal user) throws HolidayException, EmployeeException {

		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Found Holidays " );
		generalResponse.setData(holidayService.getAllHolidays(user));

		return ResponseEntity.ok(generalResponse);
	}
	

}
