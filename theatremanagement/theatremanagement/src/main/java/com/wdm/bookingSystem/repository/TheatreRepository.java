package com.wdm.bookingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

//	@Query(value = "SELECT * FROM theatre WHERE theatrename LIKE %:theatrename%", nativeQuery = true)
//	public List<Theatre> findByTheatre(String theatrename);
	
	public List<Theatre> findBytheatrenameContaining(String theatrename);  
	
	
	boolean existsByTheatrename(String theatrename);

	List<Theatre> findAllByListOfShowCinemaId(long id);

}
