package com.securecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecart.entity.Feedback;

@Repository
public interface FeedbackRepository  extends JpaRepository<Feedback, Long> {
	Feedback findByProductProductIdAndBuyerIdUserId(long productId, long userId);
}
