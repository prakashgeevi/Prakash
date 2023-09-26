package com.rentalapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestUserApproval {
	
	@NotNull(message = "UserId Id is required")
	private Long userId;
	
	@NotBlank(message = "Status is required")
	private String status;
 

}
