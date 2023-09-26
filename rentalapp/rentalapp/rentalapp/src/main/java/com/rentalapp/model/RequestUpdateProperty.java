package com.rentalapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestUpdateProperty {
	
	@NotBlank(message = "Property type is required")
	private String type; //	private room, shared room, whole house
	@NotNull(message = "CostPerDay is required")
	private double costPerDay;
	@NotBlank(message = "Room type is required")
	private String roomType;
	
	@NotNull(message = "Space is required")
	private double space;
	
	@NotBlank(message = "Floor Area Descryption is required")
	private String floorAreaDesc;
	
	@NotBlank(message = "Rental Rules is required")
	private String rentalRules;
	
	@NotBlank(message = "Location is required")
	private String location;
	
	@NotBlank(message = "Address Street is required")
	private String street;
	
	@NotBlank(message = "Address city is required")
	private String city;
	
	@NotBlank(message = "Address State is required")
	private String state;
	
	@NotBlank(message = "Address Country is required")
	private String country;
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = " Status is required")
	private String status;
	
	@NotNull(message = " OverAll Rating is required")
	private double overAllRating;
	
	@NotBlank(message = " Wireless Internet available information is required")
	private boolean wirelessInternet;  
	
	@NotNull(message = " Cooling available information is required")
	private boolean cooling;
	
	@NotNull(message = " Heating available information is required")
	private boolean heating;
	
	@NotNull(message = " kitchen available information is required")
	private boolean kitchen;
	
	@NotNull(message = " Television available information is required")
	private boolean television;
	
	@NotNull(message = " Freeparking available information is required")
	private boolean freeparking;
	
	@NotNull(message = " AC available information is required")
	private boolean ac;
	
	@NotNull(message = " Washing Machine available information is required")
	private boolean washingMachine; 
	
	@NotNull(message = " Hottub available information is required")
	private boolean hottub;
	
	@NotNull(message = " Maximum Number Of Persons Allowed information is required")
	private long maxNoOfPersonsAllowed;
	
	@NotNull(message = " Number Of Beds available information is required")
	private int noOfBeds;
	
	@NotNull(message = " Number Of Bedrooms available information is required")
	private int noOfBedrooms;
	
	@NotNull(message = " Number Of Bathrooms available information is required")
	private int noOfBathrooms;
	
	@NotNull(message = " tenant Id is required")
	private Long hostId;
	@NotNull(message = " propertySpec Id is required")
	private Long propertySpecId;
}
