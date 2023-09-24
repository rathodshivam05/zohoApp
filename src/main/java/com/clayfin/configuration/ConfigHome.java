package com.clayfin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.clayfin.jwt.JwtTokenGen;
import com.clayfin.service.UserDetailsImpService;


@Configuration
@EnableWebSecurity
public class ConfigHome extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsImpService userDetailsService;
	
	@Autowired
	JwtTokenGen jwtTokenGen;
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		System.out.println("Dao auth success");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getpasEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*
		 * auth.authenticationProvider(authenticationProvider());
		 * System.out.println("auth success");
		 */
		
		auth.userDetailsService(userDetailsService);
		
		
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests() .antMatchers("/home").permitAll()
		 * .anyRequest().authenticated().and() .authorizeRequests()
		 * 
		 * .antMatchers("/user").hasAnyRole("USER", "ADMIN").and().authorizeRequests()
		 * .antMatchers("/admin").hasAnyRole("ADMIN").and() .formLogin(withDefaults())
		 * .logout(logout -> logout.permitAll());
		 */
		
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
		exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
		and().authorizeRequests((request)->request.antMatchers("/api/v1/auth/login","/hi","/getAllEmployees").permitAll()
				.antMatchers("employee/**").hasRole("EMPLOYEE")
				//.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
		.anyRequest().authenticated())
			.addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtTokenGen), UsernamePasswordAuthenticationFilter.class);
		http.cors();
		
		http.csrf().disable().headers().frameOptions().disable();
		
		
		/*
		http.authorizeHttpRequests().antMatchers("/").permitAll().and().httpBasic().and().authorizeHttpRequests()
		.antMatchers("/admin").hasAnyRole("ADMIN").and().authorizeHttpRequests().antMatchers("/home")
		.hasAnyRole("USER");
		
	*/
		
	}
	
	@Bean
	public PasswordEncoder getpasEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
