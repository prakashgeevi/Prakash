package com.securecart.model;

import lombok.Data;
 

 @Data
public class RequestDeleteItem {
		
	private Long userId;
	
	private Long itemId;
	
	private Long cartId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
}
