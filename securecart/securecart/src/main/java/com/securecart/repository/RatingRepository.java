package com.securecart.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securecart.entity.Ratings;

public interface RatingRepository extends JpaRepository<Ratings, Long> {
	
	public List<Ratings> findByProductProductId(Long productId);

	public Ratings findByProductProductIdAndBuyerIdUserId(@NotNull Long productId, @NotNull long userId);
}
