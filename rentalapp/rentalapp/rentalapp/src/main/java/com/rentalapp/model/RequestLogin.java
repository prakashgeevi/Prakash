package com.rentalapp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestLogin {
	
	private String userName;
	
	@NotNull(message = "Password size is mismatch")
	//@Size(min = 8, max = 20)
	private String password;

	 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
