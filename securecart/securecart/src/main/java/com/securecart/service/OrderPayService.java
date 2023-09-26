package com.securecart.service;

import org.springframework.stereotype.Service;

import com.securecart.entity.OrderPayment;
import com.securecart.model.RequestOrderPay; 

@Service
public interface OrderPayService {
	
	public OrderPayment saveOrderPay(RequestOrderPay requestOrderPay);
	
	//public OrderPay refundOrderAmount(RequestRefundOrder requestRefundOrder);
	
	

}
