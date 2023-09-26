package com.securecart.serviceimpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecart.entity.Cart;
import com.securecart.entity.Items;
import com.securecart.entity.OrderPayment;
import com.securecart.entity.Orders;
import com.securecart.entity.Payments;
import com.securecart.entity.SellerPayment;
import com.securecart.entity.User;
import com.securecart.exception.IdNotFoundException;
import com.securecart.exception.ProductCustomException;
import com.securecart.model.RequestOrderPay;
import com.securecart.model.RequestPaymentModel;
import com.securecart.repository.ICartRepository;
import com.securecart.repository.IItemsRepository;
import com.securecart.repository.IOrderPayRepository;
import com.securecart.repository.IOrderRepository;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.repository.PaymentsRepository;
import com.securecart.repository.SellerPaymentRepository;
import com.securecart.service.OrderService;

@Service
public class OrderServiceimpl implements OrderService {

	@Autowired
	IOrderRepository OrderRepo;

	@Autowired
	IUserAccountRespository userRepo;

	@Autowired
	ICartRepository cartRepo;

	@Autowired
	IItemsRepository itemRepo;

	@Autowired
	SellerPaymentRepository sellerPaymentRepo;
	
	@Autowired
	PaymentsRepository paymentsRepo;
	 
	@Autowired
	IOrderPayRepository orderPayRepo;

	public Orders placeOrder(RequestOrderPay requestOrder) {
 
		try {
			UUID oid= UUID.randomUUID();
			Orders orders = new Orders();
			Cart findBycart = cartRepo.findById(requestOrder.getCartId())
					.orElseThrow(() -> new IdNotFoundException(requestOrder.getCartId() + " Not Found "));
			findBycart.setOrderStatus("INACTIVE");
			orders.setCart(findBycart);
			User user = userRepo.findById(requestOrder.getUserId())
					.orElseThrow(() -> new IdNotFoundException("user id not found"));
			orders.setUser(user);
			orders.setTotalAmount(findBycart.getTotalAmount());

			OrderPayment orderPayment =  new OrderPayment();
			//orderPayment.setCardNumber(EncryptionUtils.encrypt(requestOrder.getCardNumber()));
			orderPayment.setPaymentOption(requestOrder.getPaymentOption());
			orderPayment.setReferenceNumber(requestOrder.getReferenceNumber());
			orders.setOrderPayment(orderPayment);
			List<SellerPayment> sellerPaymentList = new ArrayList<>();
			Set<Items> list = itemRepo.findByCartCartId(requestOrder.getCartId());
			for (Items item : list) {
				if (item.getProduct().getStocks() >= item.getQuantity()) {
					item.getProduct().setStocks(item.getProduct().getStocks() - item.getQuantity());
				} else {
					throw new ProductCustomException(
							"INSUFFICIENT QUANTITY" + " Remaining stocks " + item.getProduct().getStocks());
				}
				

				SellerPayment sellerPayments = new SellerPayment();
				sellerPayments.setOrderId(oid.toString());
				sellerPayments.setOrders(orders);
				sellerPayments.setBuyer(orders.getUser());
				sellerPayments.setIsPaymentDone("PENDING");
				sellerPayments.setItem(item);
				sellerPayments.setSeller(item.getProduct().getSeller());
				sellerPayments.setTotalAmount(item.getTotalPrice());
				
				Payments payments = paymentsRepo.findBySellerUserId(item.getProduct().getSeller().getUserId());
				
				if(payments==null) {
					payments = new Payments();
					payments.setSeller(item.getProduct().getSeller());
				}
				double finalPendingPrice = payments.getPendingAmount() + item.getTotalPrice();
				double finalOverAllPrice = payments.getOverAllAmount() + item.getTotalPrice();
				payments.setPendingAmount(finalPendingPrice);
				payments.setOverAllAmount(finalOverAllPrice);
				paymentsRepo.save(payments);
				sellerPaymentList.add(sellerPayments);
			}
			sellerPaymentRepo.saveAll(sellerPaymentList);
			return OrderRepo.save(orders);

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
	}

	public void cancelOrder(long id) throws Exception {
		 
	}
	
	public Map<String, List<SellerPayment>> getAllordersBySeller(long userId){
		List<SellerPayment> respo = sellerPaymentRepo.findAllBySellerUserIdOrderByOrdersDateTimeDesc(userId);
	
		Map<String, List<SellerPayment>> collect = respo.stream().collect(Collectors.groupingBy(SellerPayment::getOrderId));
	
		return collect;
	}
	
	public Payments getAllPaymentsBySeller(long userId){
		Payments payments = paymentsRepo.findBySellerUserId(userId);
		return payments;
	}

	public Payments sendPaymentsToSeller(RequestPaymentModel data){
		Payments payments = paymentsRepo.findBySellerUserId(data.getUserId());
		double amountPending = payments.getPendingAmount() - data.getAmount();
		payments.setPendingAmount(amountPending);
		return  paymentsRepo.save(payments);
	}


	public List<Orders> getAllOrders(long userId) {

		List<Orders> orders = OrderRepo.findAllByUserUserIdOrderByDateTimeDesc(userId);

		if (orders == null) {

			throw new IdNotFoundException("products and make a purchase");
		}

		return orders;
	}
	
	public List<Orders> getAllordersForAdmin() {

		List<Orders> orders = OrderRepo.findAllByOrderByDateTimeDesc();

		return orders;
	}

	

	public List<Cart> getAllorderInActive(long userId) {

		return cartRepo.findAllByOrderStatusAndUser(userId, "INACTIVE");
	}



}
