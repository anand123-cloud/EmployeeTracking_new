package com.example.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptionhandling.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.example.service.EmployeeServiceException;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public Employee getEmpl() throws ResourceNotFoundException, EmployeeServiceException {

		Employee emp = employeeService.getEmployee();

		if (emp == null) {
			throw new ResourceNotFoundException("Employee not found");
		}
		return emp;
	}

	@RequestMapping(value = "/employee2", method = RequestMethod.GET)
	public Employee getEmp2() throws ResourceNotFoundException, EmployeeServiceException {

		Employee emp = employeeService.getEmployeeNull();
		if (emp == null) {
			throw new ResourceNotFoundException("Employee not found");
		}

		return emp;
	}

	@RequestMapping(value = "/employee3", method = RequestMethod.GET)
	public Employee getEmp3() throws ResourceNotFoundException, EmployeeServiceException {
		try {
			Employee emp = employeeService.getEmployeeException();
			if (emp == null) {
				throw new ResourceNotFoundException("Employee not found");
			}
			return emp;
		} catch (EmployeeServiceException e) {
			throw new EmployeeServiceException("Internal Server Exception while getting exception");
		}
	}
}
