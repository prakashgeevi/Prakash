package com.securecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecart.entity.Questions;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long>{

	List<Questions> findAllByBuyerIdUserIdOrderByDatetimeDesc(Long userId);

	List<Questions> findAllBySellerIdUserIdOrderByDatetimeDesc(Long userId);

	List<Questions> findAllByOrderByDatetimeDesc();

	
}
