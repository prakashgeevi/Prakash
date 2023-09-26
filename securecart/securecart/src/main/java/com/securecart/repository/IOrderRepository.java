package com.securecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securecart.entity.Orders;

public interface IOrderRepository extends JpaRepository<Orders, Long> {
	
	public List<Orders> findAllByUserUserIdOrderByDateTimeDesc(Long userId);

	public List<Orders> findAllByOrderByDateTimeDesc();

}
