package com.clayfin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.clayfin.entity.Attendance;
import com.clayfin.repository.AttendenceRepo;
import com.clayfin.utility.RepoHelper;

@Configuration
@EnableScheduling
public class ComponenetClass {
	
	@Autowired
	AttendenceRepo attendenceRepo;
	
	@Autowired
	RepoHelper repoHelper;
	
	
	@Scheduled(cron = "0 12 01 * * ?") 
	public void checkOutScheduling() {
		LocalDate fromDate = (LocalDate.now()).minusDays(1);
		List<Attendance> missedCheckouAttendance = attendenceRepo.findAllAttendancesByDate(fromDate);
		for (Attendance missedAttendance : missedCheckouAttendance) {
			if(missedAttendance.getCheckOutTimestamp()==null) {
				LocalTime time = LocalTime.of(23,59,59);  
				LocalTime checkinTime = missedAttendance.getCheckInTimestamp();
				LocalTime diff = repoHelper.findTimeBetweenTimestamps(checkinTime, time);
				LocalTime checkoutTime = checkinTime.plusHours(diff.getHour()).plusMinutes(diff.getMinute()).plusSeconds(diff.getSecond());
				missedAttendance.setCheckOutTimestamp(checkoutTime);
				LocalTime spenTime = repoHelper.findTimeBetweenTimestamps(checkinTime, checkoutTime);
				missedAttendance.setSpentHours(spenTime);
				
				
			}
			attendenceRepo.save(missedAttendance);
		}
	}
	
	
	

}
