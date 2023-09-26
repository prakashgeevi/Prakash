package com.wdm.bookingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.ShowDetails;
import com.wdm.bookingSystem.entity.Theatre;
import com.wdm.bookingSystem.model.ResponseShow;

@Repository
public interface ShowRepository extends JpaRepository<ShowDetails, Long> {

	List<ShowDetails> findByTheatrename_id(long id);

//	List<ShowDetails> findByCinema_id(long id);

//	@Query(value = "SELECT * FROM showdetails WHERE cinema = ?1 AND date = ?2", nativeQuery = true)
//	List<ShowDetails> findByCinema_idAndDate(Long cinema, String date);

//	List<ShowDetails>  findByCategoryNameContaining
//
//	 void getShowsByDateAndCinemaId(String date, long cinemaId);

	List<ShowDetails> findByDateAndCinema_Id(String date, long cinemaId);
}
