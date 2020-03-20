package com.example.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Loan {
	

	
	private Long loanAmount;
	private int noOfYears;
	public Long getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}
	public int getNoOfYears() {
		return noOfYears;
	}
	public void setNoOfYears(int noOfYears) {
		this.noOfYears = noOfYears;
	}
	 

}
