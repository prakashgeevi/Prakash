package com.securecart.serviceimpl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecart.entity.Cart;
import com.securecart.entity.Items;
import com.securecart.entity.Product;
import com.securecart.entity.User;
import com.securecart.exception.IdNotFoundException;
import com.securecart.exception.ProductCustomException;
import com.securecart.model.RequestItems;
import com.securecart.repository.ICartRepository;
import com.securecart.repository.IItemsRepository;
import com.securecart.repository.IProductMappingRespository;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.response.ResponseCart;
import com.securecart.service.CartService;

@Service
public class CartServiceimpl implements CartService {

	@Autowired

	ICartRepository cartRepo;
	
	 
	@Autowired
	IProductMappingRespository productRepo;
	
	@Autowired
	IUserAccountRespository userRepo;
	
	@Autowired
	IItemsRepository itemRepo;
	
 

	private static final Logger logger = LoggerFactory.getLogger(CartServiceimpl.class);

	public Cart saveCart(RequestItems requestitem) {

		logger.info("CartServiceimpl | cart is called");
		try {
			 
			 User user = userRepo.findById(requestitem.getUserId())
						.orElseThrow(() -> new IdNotFoundException("userId not found"));
			 
			Cart cart = cartRepo.findByOrderStatusAndUser(requestitem.getUserId(), "ACTIVE");
			 
			if(cart == null) {
				cart = new Cart();
				cart.setOrderStatus("ACTIVE");
			} 
			  
			Product product = productRepo.findById(requestitem.getProductId())
					.orElseThrow(() -> new IdNotFoundException("product id not found"));
			Items productId = itemRepo.findByProductProductIdAndCartCartId(requestitem.getProductId(), cart.getCartId());
			
			if(productId != null) {
				throw new IdNotFoundException("This product already added in your cart");
			}
			 
			 Items item = new Items();
				item.setQuantity(requestitem.getQuantity());
				
			
			 item.setProduct(product);
				
			 double totalPriceValue =  product.getPrice() * requestitem.getQuantity();
			 
				
			 item.setTotalPrice(totalPriceValue);
			  
			 Set<Items> itemList = cart.getItem();
			 item.setCart(cart);
			 itemList.add(item);
			   
			 cart.setItem(itemList);
			  
			 Set<Items> itemlist = itemRepo.findByCartCartId(cart.getCartId());
				
			   
				double totalAmount= 0;
					for(Items itemcart : itemlist) {
					 
					totalAmount += itemcart.getTotalPrice();
					
				}
				
				cart.setTotalAmount(totalAmount + totalPriceValue);
				
				 
			 cart.setUser(user);
				
			 return cartRepo.save(cart);
			 
	}
		catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
	}
		
	}

	
	
	
	
	public Cart getCartByUser(ResponseCart responseCart) {
		Cart cart= new Cart();
		try {
		 	
		cart = cartRepo.findByOrderStatusAndUser(responseCart.getUserId(), "ACTIVE");
		 
		if(cart==null) {
			//throw new IdNotFoundException("cart is empty");
		}
		 
		return cart;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	 
}
