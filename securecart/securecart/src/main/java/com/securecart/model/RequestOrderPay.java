package com.securecart.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestOrderPay {
	
	
	@NotBlank(message = "Reference Number is required")
	private String referenceNumber;
	@NotBlank(message = "PaymentOption is required")
	private String paymentOption;
	
	@NotNull(message = "cartId size is missing")
	private Long cartId;
	  
	@NotNull(message = "userId size is missing")
	private Long userId;
	 
	private LocalDateTime dateTime=LocalDateTime.now();
	 
	
	

	}
