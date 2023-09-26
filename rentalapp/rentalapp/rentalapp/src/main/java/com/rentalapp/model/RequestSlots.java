package com.rentalapp.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestSlots {
	
	@NotNull(message = "Property id is required")
	private Long propertyId;
	
	
	@NotEmpty(message = "available Days can't empty")
	private List<PropertySlotDateRange> availableDays;
	
}
