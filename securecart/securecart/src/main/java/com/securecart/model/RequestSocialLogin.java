package com.securecart.model;

import javax.validation.constraints.Email;
 
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
 

public class RequestSocialLogin {
	
	
	@NotBlank(message = "Please give the first name")
	private String firstName;
	
	@NotBlank(message = "Please give the last name")
	private String lastName;
	
	@NotBlank(message = "Email can't empty")
	@Email(message = "email formatting missing")
	private String email;
	
	@NotBlank(message = "password can't empty")
	@Size(min = 8)
	private String password;
	
	@NotBlank(message = "Role cannot be blank")
	private String role;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
