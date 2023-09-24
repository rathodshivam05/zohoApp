package com.clayfin.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.clayfin.jwt.JwtTokenGen;
import com.clayfin.service.UserDetailsImpService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private UserDetailsImpService userDetailsImpService;
	
	private JwtTokenGen jwtTokenGen;
	
	
	public JwtAuthenticationFilter(UserDetailsImpService userDetailsImpService,JwtTokenGen jwtTokenGen) {
		this.userDetailsImpService=userDetailsImpService;
	
		this.jwtTokenGen = jwtTokenGen;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken;
		
		authToken = jwtTokenGen.getToken(request);
		if(null!=authToken) {
			String userName = jwtTokenGen.getUsernameFromToken(authToken);
			if(null!=userName) {
				UserDetails user = userDetailsImpService.loadUserByUsername(userName);
				System.out.println("role :"+user.getAuthorities().toString());
				if(jwtTokenGen.validateToken(authToken, user)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	
}
