package com.rentalapp.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestGetAllMessage {
	
	@NotNull(message = "Property id is required")
	private Long propertyId;
	
	@NotNull(message = "From user id is required")
	private Long fromId;
	
	@NotNull(message = "To user id is required")
	private Long toId;
	

}
