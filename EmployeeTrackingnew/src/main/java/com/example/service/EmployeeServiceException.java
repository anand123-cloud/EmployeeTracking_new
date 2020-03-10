package com.example.service;



import org.springframework.stereotype.Service;
import com.example.model.Employee;

@Service
public class EmployeeServiceException {
	

	public class EmployeeServiceException extends Exception {

		private static final long serialVersionUID = -470180507998010368L;

		public EmployeeServiceException() {
			super();
		}

		public EmployeeServiceException(final String message) {
			super(message);
		}
	}
}

	