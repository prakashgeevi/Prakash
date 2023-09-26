package com.securecart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.securecart.entity.Cart;
import com.securecart.model.RequestCart;
import com.securecart.model.RequestItems;
import com.securecart.response.ResponseCart;

@Service
public interface CartService {

	
	public Cart saveCart(RequestItems requestitem);
	
	
	
	public Cart getCartByUser(ResponseCart responseCart);

	 
	
	
}
