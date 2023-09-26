package com.securecart.service;

 

import org.springframework.stereotype.Service;

import com.securecart.entity.Items;
import com.securecart.model.RequestItems;

@Service
public interface ItemService {
	
	 
	
	public void deleteByid(long itemId, long cartId, long userId);
	
//	public Items getItemsByid(long id) throws Exception;
	
  
	public Items updatecategory(RequestItems items, long id);
	
}
