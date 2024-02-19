package com.cdac.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.config.JwtTokenUtility;
import com.cdac.dto.LoginDetails;
import com.cdac.dto.RegistrationStatus;
import com.cdac.entity.Employee;
import com.cdac.service.EmployeeService;

@RestController
@CrossOrigin
public class EmployeeController {
	
	@Autowired AuthenticationManager authenticationManager;

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private JwtTokenUtility jwtToken;
	
	@PostMapping("/employee/register")
	public RegistrationStatus register(@RequestBody Employee employee) {
		
			boolean b = employeeService.register(employee);
			RegistrationStatus status = new RegistrationStatus();

			if(b){
				status.setStatus(true);
				status.setStatusMessage("Customer registered successfully!");
			} else {
				status.setStatus(false);
				status.setStatusMessage("Customer already registered");
			}
		return status;
	}
	/*
	@PostMapping("/employee/login")
	public Status login(@RequestBody LoginDetails loginDetails) {
		Employee employee = employeeService.login(loginDetails.getEmail(), loginDetails.getPassword());
		if(employee != null) {			
			EmployeeLoginStatus status = new EmployeeLoginStatus();
			status.setStatus(true);
			status.setMessageIfAny("Login successful!");
			status.setEmployeeId(employee.getEmployee_id());
			status.setName(employee.getEmployeeName());
			return status;
		}
		else  {
			Status status = new Status();
			status.setStatus(false);
			status.setMessageIfAny("Invalid loginId/Password");
			return status;
		}
	}
	*/
	@PostMapping("/employee/login")
	public ResponseEntity<?> login(@RequestBody LoginDetails loginDetails) throws InvalidKeySpecException, NoSuchAlgorithmException{
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Employee emp1 = (Employee)authentication.getPrincipal(); 
		String token = jwtToken.generateToken(emp1.getEmail());
		return ResponseEntity.ok(token);
	}
}