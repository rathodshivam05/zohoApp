package com.clayfin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Employee;
import com.clayfin.entity.MyUserDetails;
import com.clayfin.repository.EmployeeRepo;

@Service
public class UserDetailsImpService implements UserDetailsService {
	
	
	@Autowired
	EmployeeRepo  employeeRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Employee user = employeeRepo.findByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
		
	    return new MyUserDetails(user);
	    //return user.map(MyUserDetails::new).get();

	}
	
	
	
}
