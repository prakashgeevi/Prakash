package com.securecart.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestFeedback {
	
	@NotNull
	private Long productId;
	
	@NotNull
	private long userId;
	
	@NotBlank(message = "Reviews cannot be blank")
	private String message;
}
