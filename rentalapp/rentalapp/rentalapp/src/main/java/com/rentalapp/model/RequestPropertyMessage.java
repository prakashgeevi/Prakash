package com.rentalapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestPropertyMessage {
	
	@NotNull(message = " from userId Id is required")
	private Long fromId;
	
	@NotNull(message = "To userId Id is required")
	private Long toId;
	
	@NotNull(message = " Property Id is required")
	private Long propertyId;
	
	@NotBlank(message = " Messages Id is required")
	private String Message;
}
