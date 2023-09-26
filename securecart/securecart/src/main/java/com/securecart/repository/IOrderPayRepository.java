package com.securecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecart.entity.OrderPayment;

@Repository
public interface IOrderPayRepository extends JpaRepository<OrderPayment, Long> {

	 
}
