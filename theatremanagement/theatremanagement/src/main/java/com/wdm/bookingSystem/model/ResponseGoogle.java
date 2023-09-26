package com.wdm.bookingSystem.model;

import javax.validation.constraints.NotBlank;

public class ResponseGoogle {

	@NotBlank(message = "email is Mandatory")
	private String email;
	
	@NotBlank(message = "password is Mandatory")
	private String password;
	
	@NotBlank(message = "role is Mandatory")
	private String role;
	
	@NotBlank(message = "userName is Mandatory")
	private String userName;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
