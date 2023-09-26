package com.rentalapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestReservation {
	
	@NotNull(message = "Property id is required")
	private Long propertyId;
	
	@NotNull(message = "User id is required")
	private Long tenantId;
	
	@NotBlank(message = "CheckInDate is required")
	private String checkInDate;
	
	@NotBlank(message = "CheckOutDate is required")
	private String checkOutDate;
	
	@NotNull(message = "User id is required")
	private Long numberOfPersonStay;
	
//	@NotBlank(message = "paymentType is required")
//	private String paymentType;
//	
//	@NotNull(message = "amount is required")
//	private double amount;
//	
//	@NotNull(message = "transactionId is required")
//	private String transactionId;
	 
	
}
