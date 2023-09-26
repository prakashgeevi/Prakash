package com.wdm.bookingSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.Booking;
import com.wdm.bookingSystem.model.BookingResponce;
import com.wdm.bookingSystem.model.RequestBooking;

@Service
public interface IBookingService {

	public Booking savebooking(RequestBooking booking);

	public List<Integer> getSeatsByBookingAndShow(long showId);

	public List<BookingResponce> findBookingDetails(long id);

}
