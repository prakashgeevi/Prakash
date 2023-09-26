package com.securecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecart.entity.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
	Payments findBySellerUserId(long userId);
}
