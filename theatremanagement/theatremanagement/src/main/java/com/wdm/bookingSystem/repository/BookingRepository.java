package com.wdm.bookingSystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wdm.bookingSystem.entity.Booking;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	 public  List<Booking> findByUser(long id);
	 
	 public  List<Booking> findByShowdetailsId(long id);
	
}
