package com.wdm.bookingSystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestTheatre {

	@NotBlank(message = "Theatrename is mandatory")
	@Size(min = 4, max = 15)
	
	private String theatrename;
	
	@NotNull(message = "userid is mandatory")
	private long userid;

	@NotBlank(message = "street is mandatory")
	private String street;

	@NotBlank(message = "city is mandatory")
	private String city;

	@NotBlank(message = "state is mandatory")
	private String state;

	@NotBlank(message = "pincode is mandatory")
	private String pincode;

	@NotNull(message = "numberofrows is mandatory")
	private long numberofrows;

	//@NotBlank(message = "numberofseats is mandatory")
	@Size(min = 0, max = 10, message = "Number should have at least 0 or less than 10 digits")
	private long numberofseats;

	@NotNull
	private long phoneNumber;
	

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

	public String getTheatrename() {
		return theatrename;
	}

	public void setTheatrename(String theatrename) {
		this.theatrename = theatrename;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getNumberofrows() {
		return numberofrows;
	}

	public void setNumberofrows(long numberofrows) {
		this.numberofrows = numberofrows;
	}

	public long getNumberofseats() {
		return numberofseats;
	}

	public void setNumberofseats(long numberofseats) {
		this.numberofseats = numberofseats;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	
}
