package com.cdac.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.cdac.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	/*@Query("select count(c) from Employee c where c.email = ?1")
	public Long findIfCustomerExists(String email);
	
	@Query("select count(c) from Employee c where c.email = ?1 and c.password = ?2")
	public Long findIfCustomerIsPresent(String email, String password);*/
	
//	public Optional<Employee> findByEmail(String email);
	public Optional<Employee> findByEmailAndPassword(String email, String password);

	public Employee findByEmail(String username);

	public Optional<Employee> findByEmailId(String email);
}
