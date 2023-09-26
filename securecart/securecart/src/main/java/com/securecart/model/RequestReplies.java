package com.securecart.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestReplies {

	@NotBlank(message = "ReplyMessage is is required")
	private String replyMessage;
	
	@NotNull(message = "questionsId is is required")
	private Long questionsId;
	
	@NotNull(message = "buyerId is is required")
	private Long buyerId;
	
}
