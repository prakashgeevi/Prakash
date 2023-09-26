package com.securecart.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestRatings {
	@NotNull
	private Long productId;
	
	@NotNull
	private long userId;
	
	@NotNull(message = "Rating cannot be null")
	private Double rating;
}
