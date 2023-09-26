package com.securecart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_payment")
@Getter
@Setter
public class OrderPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderPayId;
	
	private String referenceNumber;
	
	private String paymentOption;
	 
}
