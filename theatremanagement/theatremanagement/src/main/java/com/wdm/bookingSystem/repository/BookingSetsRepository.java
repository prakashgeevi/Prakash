package com.wdm.bookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.BookingSeats;
@Repository
public interface BookingSetsRepository extends JpaRepository<BookingSeats, Integer>{

}
