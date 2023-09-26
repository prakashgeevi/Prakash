package com.wdm.bookingSystem.model;




public class RequestLogin {

//	@NotBlank(message = "useName Is must")
	private String userName;

//	@NotBlank(message = "password Is must")
	private String password;

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
