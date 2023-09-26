package com.wdm.bookingSystem.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.wdm.bookingSystem.entity.Booking;
import com.wdm.bookingSystem.model.BookingResponce;
import com.wdm.bookingSystem.model.RequestBooking;
import com.wdm.bookingSystem.service.IBookingService;

@RequestMapping("/Booking")
@RestController
@CrossOrigin
public class BookingController {

	@Autowired
	IBookingService iBookingService;

	private static final Logger logger = LoggerFactory.getLogger(TheatreController.class);
	
	
	

	@PostMapping
	public ResponseEntity<Booking> savebooking(@RequestBody RequestBooking booking) {
		logger.info("creat Booking", booking.getSeatNumber());
		return new ResponseEntity<>(iBookingService.savebooking(booking), HttpStatus.OK);
	}

	@GetMapping("userBook/{id}")
	public ResponseEntity<List<BookingResponce>> getUserByBooking(@PathVariable("id") long id) {
		logger.info("get a Booking");
		return new ResponseEntity<>(iBookingService.findBookingDetails(id), HttpStatus.OK);

	}

	@GetMapping("show/{seatId}")
	public ResponseEntity<List<Integer>> getSeatsByBookingAndShow(@PathVariable("seatId") long seatId) {
		logger.info("get a Booking");
		return new ResponseEntity<>(iBookingService.getSeatsByBookingAndShow(seatId), HttpStatus.OK);

	}

}
