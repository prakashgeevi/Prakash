package com.rentalapp.model;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class RequestRating {
	
	@NotNull(message = "Property Id is required")
	private Long propertyId;
	
	@NotNull(message = "UserId Id is required")
	private long userId;
	
	@NotNull(message = "Rating cannot be null")
	private Double rating;
	
	private String review;
	
	
}
