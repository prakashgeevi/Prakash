package com.wdm.bookingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long>

{
	
//	@Query(value = "SELECT * FROM cinema WHERE movie_name LIKE %:movieName%", nativeQuery = true)
//	public List<Cinema> findByCinema(String movieName);
		
	public List<Cinema> findByMovieNameContaining(String movieName);

}
