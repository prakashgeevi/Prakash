package com.wdm.bookingSystem.model;


import javax.validation.constraints.NotBlank;

public class RequestAddress {
	
	@NotBlank(message = "street is mandatory" )
	private String street;
	
	@NotBlank(message = "city is mandatory" )
	private String city;
	
	@NotBlank(message = "state is mandatory" )
	private String state;
	
	@NotBlank(message = "pincode is mandatory" )
	private String pincode;
	
	@NotBlank(message = "phoneNumber is mandatory" )
	private String phoneNumber;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public RequestAddress(String street, String city, String state, String pincode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
