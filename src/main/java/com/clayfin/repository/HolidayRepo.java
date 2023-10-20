package com.clayfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clayfin.entity.Holidays;

@Repository
public interface HolidayRepo extends JpaRepository<Holidays, Integer>{
	
}
