package com.rentalapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestHostReply {
	@NotNull(message = " from userId Id is required")
	private Long fromId;
	
	@NotNull(message = "convertId is required")
	private String convertId;
	
	@NotBlank(message = " Messages Id is required")
	private String Message;
}
