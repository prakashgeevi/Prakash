package com.securecart.serviceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecart.entity.OrderPayment;
import com.securecart.model.RequestOrderPay;
import com.securecart.repository.IOrderPayRepository;
import com.securecart.service.OrderPayService;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	
	@Autowired 
	IOrderPayRepository orderPayRepo;
	
	
	 
	public OrderPayment saveOrderPay(RequestOrderPay requestOrderPay) {
		 
		OrderPayment orderPayment = new OrderPayment();
		
		 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		//LocalDateTime txnDateTime =  LocalDateTime.parse(requestOrderPay.getTxnDate(), formatter);
		
//		orderPayment.setTxnDate(txnDateTime);
//		orderPayment.setTxnId(requestOrderPay.getTxnId());
		
		return orderPayRepo.save(orderPayment);
	}

 

}
