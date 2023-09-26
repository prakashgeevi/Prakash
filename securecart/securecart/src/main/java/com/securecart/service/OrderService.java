package com.securecart.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.securecart.entity.Cart;
import com.securecart.entity.Orders;
import com.securecart.entity.Payments;
import com.securecart.entity.SellerPayment;
import com.securecart.model.RequestOrderPay;
import com.securecart.model.RequestPaymentModel;

@Service
public interface OrderService {
	
	public Orders placeOrder(RequestOrderPay requestOrder);
	
	public void cancelOrder(long id) throws Exception;
	
 
	
	public List<Orders> getAllOrders(long userId);
	
	 
	
	
	public List<Cart> getAllorderInActive(long userId);

	public Map<String, List<SellerPayment>> getAllordersBySeller(long userId);

	public Payments getAllPaymentsBySeller(long userId);

	public Payments sendPaymentsToSeller(RequestPaymentModel data);

	public List<Orders> getAllordersForAdmin();
	
	
}
