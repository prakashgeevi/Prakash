package com.wdm.bookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.Actors;
import com.wdm.bookingSystem.model.RequestCinema;

@Repository
public interface ActorsRepository extends JpaRepository<Actors, Long> {



}
