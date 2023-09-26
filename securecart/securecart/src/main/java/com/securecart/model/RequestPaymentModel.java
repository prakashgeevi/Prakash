package com.securecart.model;

import lombok.Data;

@Data
public class RequestPaymentModel {
	private long userId;
	private double amount;
}
