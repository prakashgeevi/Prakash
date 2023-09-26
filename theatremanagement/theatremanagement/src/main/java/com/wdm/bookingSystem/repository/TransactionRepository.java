package com.wdm.bookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.TransactionDetail;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetail, Long> {

}
