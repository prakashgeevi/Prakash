package com.securecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecart.entity.Replies;
import com.securecart.entity.SellerPayment;

@Repository
public interface SellerPaymentRepository extends JpaRepository<SellerPayment, Long>{

	List<SellerPayment> findAllBySellerUserIdOrderByOrdersDateTimeDesc(long userId);

}
