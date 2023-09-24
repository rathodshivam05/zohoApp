package com.clayfin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clayfin.dto.GeneralResponse;
import com.clayfin.entity.Employee;
import com.clayfin.entity.JwtService;
import com.clayfin.entity.LoginResponse;
import com.clayfin.entity.MyUserDetails;
import com.clayfin.jwt.JwtTokenGen;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.service.UserDetailsImpService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticatedController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenGen jwtTokenGen;
	
	@Autowired
	EmployeeRepo userRepo;
	
	@Autowired
	UserDetailsImpService detailsImpService;
	//api/v1/auth/login"
	
	@PostMapping("auth/login")
	public ResponseEntity<GeneralResponse> login(@RequestBody JwtService jwtService){
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtService.getUsername(), jwtService.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		MyUserDetails user =(MyUserDetails) authentication.getPrincipal();
		String jwtToken = jwtTokenGen.generateToken(user);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		
		
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("JWT Token Generated");
		generalResponse.setData(loginResponse);
		return  ResponseEntity.ok(generalResponse); //ResponseEntity.ok(loginResponse);
	}
	
	
	@GetMapping("auth/userinfo")
	public ResponseEntity<GeneralResponse>  userinfo(Principal user){
		//MyUserDetails details = (MyUserDetails)detailsImpService.loadUserByUsername(user.getName());
		//User userDetails = (User)detailsImpService.loadUserByUsername(user.getName());
		Employee user2 = userRepo.findByUsername(user.getName());
		
		var generalResponse = new GeneralResponse();
		generalResponse.setMessage("Employee Added");
		generalResponse.setData(user2);

		return  ResponseEntity.ok(generalResponse); //ResponseEntity.ok(user2);
	}
}
