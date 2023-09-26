package com.securecart.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securecart.entity.OrderPayment;
import com.securecart.model.RequestOrderPay;
import com.securecart.service.OrderPayService;

@RestController
@RequestMapping("/orderPay")
@CrossOrigin("*")
public class OrderPayController {

	private static final Logger logger = LogManager.getLogger(OrderPayController.class);

	@Autowired
	OrderPayService orderPayService;

	@PostMapping
	public ResponseEntity<?> saveOrderPay(@RequestBody RequestOrderPay requestOrderpay) {

		return new ResponseEntity<OrderPayment>(orderPayService.saveOrderPay(requestOrderpay), HttpStatus.CREATED);
	}

	@PostMapping("/refund")
	public ResponseEntity<?> refundAmount(@RequestBody RequestOrderPay orderPay) {
		return null;

	}

}
