package com.rentalapp.model;

import javax.validation.constraints.Email;

public class RequestConfirmPassword {
	
	@Email
	private String Email;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
	
}
