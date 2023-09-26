package com.rentalapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	
	public List<Property> findAllByStatus(String status);
	
	public List<Property> findAllByUserIdOrderByCreatedAtDesc(Long userId);
	
	public Property findByIdAndUserId(Long propertyId, Long userId);
	
	public List<Property> findByIdIn(List<Long> propertyidList);
	
	public List<Property> findByCityOrStateOrCountry(String city, String state, String country);
	
	public List<Property> findByCityOrStateOrCountryOrPropertySlotDateEqualsAndPropertySlotDateLessThanEqual
							(String city, String state, String country, LocalDate startDate, LocalDate endDate);
	
	
	public List<Property> findDistinctByOrderByOverAllRatingDesc();

	public List<Property> findDistinctByCityAndStateAndCountry(String city, String state, String country);

	public List<Property> findDistinctByStateAndCountry(String state, String country);

	public List<Property> findDistinctByCountry(String country);
	
}
