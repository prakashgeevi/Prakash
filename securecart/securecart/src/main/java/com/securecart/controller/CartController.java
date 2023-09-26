package com.securecart.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.securecart.entity.Cart;
import com.securecart.model.RequestItems;
import com.securecart.response.ResponseCart;
import com.securecart.service.CartService;

@RestController

@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

	@Autowired
	CartService cartservice;

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@PostMapping
	public ResponseEntity<Cart> saveCart(@Valid @RequestBody RequestItems requestitem) {

		logger.info("save new product = TotalPrice={}, productId= {} ", requestitem.getProductId());

		return new ResponseEntity<>(cartservice.saveCart(requestitem), HttpStatus.CREATED);
	}

	@PostMapping("/data")
	public ResponseEntity<Cart> getAllbyuser(@Valid @RequestBody ResponseCart responseCart) {

		logger.info("get  cart data = userId={}, orderStatus= {} ", responseCart.getUserId(),
				responseCart.getOrderStatus());

		Cart cart = cartservice.getCartByUser(responseCart);

		logger.info("get cart item={} : " + cart);

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

}
