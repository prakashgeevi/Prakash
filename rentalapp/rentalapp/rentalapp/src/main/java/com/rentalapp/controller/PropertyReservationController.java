package com.rentalapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentalapp.entity.PropertyReservation;
import com.rentalapp.model.RequestReservation;
import com.rentalapp.service.ReservationService;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin("*")
public class PropertyReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	
	private static final Logger logger = LogManager.getLogger(PropertyReservationController.class);
	
	@PostMapping
	public ResponseEntity<PropertyReservation> saveReservation(@Valid @RequestBody RequestReservation requestReservation) {
		logger.info("Reservations created successfully");
		return new ResponseEntity<PropertyReservation>(reservationService.reserveProperty(requestReservation),
				HttpStatus.CREATED);
	}
	
	@GetMapping("/{tenantId}")
	public ResponseEntity<?> getReservationList(@PathVariable("tenantId") Long tenantId) {
		logger.info("Get All Reservations with tenantId={}", tenantId);
		List<PropertyReservation> reservePropertyList = reservationService.getAllReservePropertyList(tenantId);
		logger.info("Get All Reservations successfully with totalSize={}", reservePropertyList.size());
		return new ResponseEntity<>(reservePropertyList, HttpStatus.OK);
	}
}
