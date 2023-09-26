package com.securecart.serviceimpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecart.entity.Cart;
import com.securecart.entity.Items;
import com.securecart.entity.Product;
import com.securecart.exception.IdNotFoundException;
import com.securecart.exception.ProductCustomException;
import com.securecart.model.RequestItems;
import com.securecart.repository.ICartRepository;
import com.securecart.repository.IItemsRepository;
import com.securecart.repository.IProductMappingRespository;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.service.ItemService;

@Service
public class ItemServiceimpl implements ItemService {

	@Autowired
	IItemsRepository itemRepo;

	@Autowired
	IProductMappingRespository productRepo;

	@Autowired
	ICartRepository cartRepo;

	@Autowired
	IUserAccountRespository userRepo;

	private static final Logger logger = LogManager.getLogger(ItemServiceimpl.class);

	

	public void deleteByid(long itemId, long cartId, long userId) {

		Optional<Cart> cart = cartRepo.findById(cartId);

		if (cart.isPresent()) {
			Set<Items> itemList = cart.get().getItem();
			Optional<Items> itemToRemove = itemList.stream().filter(e -> e.getItemId() == itemId).findFirst();

			logger.info("To get item data={}", itemId);
			
			if (itemToRemove.isPresent()) {

				itemList.remove(itemToRemove.get());

				cartRepo.save(cart.get());
			} else {
				throw new EntityNotFoundException("Item with id " + itemId + " not found in cart with id " + cartId);
			}
		} else {
			throw new EntityNotFoundException("Cart with id " + cartId + " not found");
		}

		Cart cart2 = cartRepo.findByOrderStatusAndUser(userId, "ACTIVE");

		Set<Items> item = itemRepo.findByCartCartId(cart2.getCartId());

			updateTotalPrice(item, cart2);
		 
	}

 	 

//	public Items getItemsByid(long id) throws Exception {
//		try {
//			return itemRepo.findById(id).get();
//
//		} catch (Exception idNotFoundException) {
//			throw new IdNotFoundException(idNotFoundException.getMessage());
//		}
//
//	}

	public Items updatecategory(RequestItems items, long id) {

		Items items1 = itemRepo.findById(id).orElseThrow(() -> new IdNotFoundException("Not Found" + id));

		items1.setQuantity(items.getQuantity());

		Product product = productRepo.findById(items.getProductId())
				.orElseThrow(() -> new IdNotFoundException("product id not found"));

		double total = product.getPrice() * items.getQuantity();

		items1.setTotalPrice(total);

		items1.setProduct(product);

		Cart cart = cartRepo.findById(items1.getCart().getCartId())
				.orElseThrow(() -> new IdNotFoundException(" cart Not found "));

		Set<Items> itemlist = itemRepo.findByCartCartId(cart.getCartId());

			updateTotalPrice(itemlist, cart);

		return itemRepo.save(items1);

	}
	
	public void updateTotalPrice(Set<Items> items, Cart cart) {
		
		double totalAmount = 0;
		for (Items itemcart : items) {

			totalAmount += itemcart.getTotalPrice();

		}

		cart.setTotalAmount(totalAmount);

		cartRepo.save(cart);
		
	}
	

 

}
