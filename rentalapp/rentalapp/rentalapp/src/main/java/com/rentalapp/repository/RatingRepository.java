package com.rentalapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, String> {
	
	public List<Rating> findByPropertyId(Long id);

	public Rating findByPropertyIdAndUserId(Long propertyId, long userId);
}
