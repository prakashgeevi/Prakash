package com.wdm.bookingSystem.model;

import javax.validation.constraints.Email;
public class ResponseForget {

	@Email
	private String Email;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
