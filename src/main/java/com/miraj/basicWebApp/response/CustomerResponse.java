package com.miraj.basicWebApp.response;

import java.util.List;

import com.miraj.basicWebApp.model.Customer;

public class CustomerResponse {
	
	List<Customer> cusList;
	String message;

	public List<Customer> getCusList() {
		return cusList;
	}

	public void setCusList(List<Customer> cusList) {
		this.cusList = cusList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
