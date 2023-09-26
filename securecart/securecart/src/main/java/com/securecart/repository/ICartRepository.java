package com.securecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.securecart.entity.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
	
	 
	
	
 		@Query(value = "select * from cart where user=:userId and order_status=:orderStatus", nativeQuery = true)
	public Cart findByOrderStatusAndUser(Long userId, String orderStatus);
	
	
   @Query(value = "select * from cart where user=:userid and order_status=:status", nativeQuery = true)
	public List<Cart> findAllByOrderStatusAndUser(Long userid, String status);
	
  
 
	
	
}
