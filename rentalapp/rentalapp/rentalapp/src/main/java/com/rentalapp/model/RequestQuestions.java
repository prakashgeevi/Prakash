package com.rentalapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestQuestions {
	
	@NotNull(message = "UserId Id is required")
	private Long userId;
	
	@NotNull(message = "Property Id is required")
	private Long propertyId;
	
	@NotBlank(message = "Question is required")
	private String question;
	
	@NotBlank(message = "Answers is required")
	private String answers;

}
