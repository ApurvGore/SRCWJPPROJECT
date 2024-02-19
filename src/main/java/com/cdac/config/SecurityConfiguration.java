package com.cdac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cdac.service.EmployeeService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(employeeService).passwordEncoder(passwordEncoder());
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
			.and().authorizeRequests((request)->request.antMatchers("/employee/login").permitAll()
			.antMatchers("/employee/**").hasAnyAuthority("USER","ADMIN")
			.antMatchers("/vendor/**").hasAuthority("VENDOR")
			.antMatchers(HttpMethod.OPTIONS,"/**").permitAll())
			.addFilterBefore(new JwtAuthenticationFilter(employeeService, jwtTokenHelper),UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable().cors().and().headers().frameOptions().disable();
	}

}
