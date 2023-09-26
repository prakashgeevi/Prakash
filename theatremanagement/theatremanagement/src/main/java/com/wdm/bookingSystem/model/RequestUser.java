package com.wdm.bookingSystem.model;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class RequestUser {

	@NotBlank(message = "userName is mandatory")
	private String userName;

	@Email
	@NotBlank(message = "Email is mandatory")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "New password is mandatory")
	@Size(min = 8, max = 20)
	private String password;

	@NotBlank(message = "Mobile is mandatory")
	@Size(min = 10, max = 10)
	private String mobile;

	@NotBlank(message = "Role is Mandatory")
	private String role;

	
	public String getUserName() {
		return userName;
	}

	
	public String getEmail() {
		return email;
	}

	
	public String getPassword() {
		return password;
	}

	
	public String getMobile() {
		return mobile;
	}

	
	public String getRole() {
		return role;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	
	

	
}
