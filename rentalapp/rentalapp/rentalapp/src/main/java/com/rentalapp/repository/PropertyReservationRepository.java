package com.rentalapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.PropertyReservation;

public interface PropertyReservationRepository extends JpaRepository<PropertyReservation, Long> {

	public PropertyReservation findByCheckInDateAndCheckOutDate(LocalDate checkIndDate, LocalDate checkOutdDate);

//	public PropertyReservation findByPropertyIdAndCheckInDateAndCheckOutBetween(
//			LocalDate checkIndDate, LocalDate checkOutdDate, Long propertId);

	public List<PropertyReservation> findByUserId(Long tenantId);

}
