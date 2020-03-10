package com.example.service;

import com.example.model.Employee;

public class EmployeeService {
	public Employee getEmployee() throws EmployeeServiceException {
		Employee emp = new Employee();
		emp.setName("emp1");
		emp.setDesignation("manager");
		emp.setEmpId("1");
		emp.setSalary(3000);

		return emp;
	}

	public Employee getEmployeeNull() throws EmployeeServiceException {

		return null;
	}

	public Employee getEmployeeException() throws EmployeeServiceException {

		throw new EmployeeServiceException();
	}

}


}
