package com.rentalapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.PropertySlots;

public interface PropertySlotRepository extends JpaRepository<PropertySlots, Long> {
	
	public List<PropertySlots> findByAndPropertyIdAndStatusAndDateIn(Long propertyId, String status, List<LocalDate> dateList);
	
	public List<PropertySlots> findByPropertyIdAndStatusNot(Long propertyId, String status);
	 
}
