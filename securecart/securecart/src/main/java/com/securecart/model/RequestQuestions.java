package com.securecart.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestQuestions {

	@NotBlank
	private String question;

	@NotNull
	private Long buyerId;

	@NotNull
	private Long sellerId;

}
