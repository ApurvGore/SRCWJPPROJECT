package com.cdac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.EmployeeLoginStatus;
import com.cdac.dto.LoginDetails;
import com.cdac.dto.LoginStatus;
import com.cdac.dto.RegistrationStatus;
import com.cdac.dto.Status;
import com.cdac.entity.Employee;
import com.cdac.exception.EmployeeServiceException;
import com.cdac.service.EmployeeService;

@RestController
@CrossOrigin
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/employee/register")
	public RegistrationStatus register(@RequestBody Employee employee) {
		
			boolean b = employeeService.register(employee);
			if(b){
			RegistrationStatus status = new RegistrationStatus();
			status.setStatus(true);
			status.setStatusMessage("Customer registered successfully!");
			return status;
			}
			
			else {
			RegistrationStatus status = new RegistrationStatus();
			status.setStatus(false);
			status.setStatusMessage("Customer already registered");
			return status;		
			}
		
	}
	
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
	
}