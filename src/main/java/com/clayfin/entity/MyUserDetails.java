package com.clayfin.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.clayfin.enums.RoleType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MyUserDetails implements UserDetails{
	//private String username ;
	//private String password;
	//private List<GrantedAuthority> authorities;
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	private String userName;
	private String password;
	
	private List<GrantedAuthority> authorities;

	public MyUserDetails(Employee user) {
		
		this.userName = user.getUsername();
		this.password = user.getPassword();
		
		
		this.authorities = Arrays.stream(user.getRole().toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	//User user;

	public MyUserDetails() {
		
	}






	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return  authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isActive() {
		return true;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	
	  public void setAuthorities(List<GrantedAuthority> authorities) {
		  
	  
		  this.authorities = authorities;
	  }
	 
	
	

}
