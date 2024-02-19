package com.cdac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cdac.entity.Employee;
import com.cdac.repository.EmployeeRepository;

@Service
public class EmployeeService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return employeeRepository.findByEmail(username);
	}

	
	public boolean register(Employee employee) {
		//suppose we need to check if this customer has already registered before
		Optional<Employee> employeeCheck = employeeRepository.findByEmailId(employee.getEmail());
		if(!employeeCheck.isPresent()) {
			Employee savedEmployee = employeeRepository.save(employee);
			return true;
		}
		else			
			return false;
	}

	public Employee login(String email, String password) {
		Optional<Employee> employee = employeeRepository.findByEmailAndPassword(email, password);
		if(employee.isPresent()) {
			System.out.println(employee.get());
			return employee.get();
		}
		else {
			//throw new EmployeeServiceException("Invalid Email/Password");
			return null;
		}
	}
}
