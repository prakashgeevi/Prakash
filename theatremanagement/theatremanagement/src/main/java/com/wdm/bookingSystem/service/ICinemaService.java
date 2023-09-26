package com.wdm.bookingSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.Cinema;
import com.wdm.bookingSystem.model.RequestCinema;

@Service
public interface ICinemaService {

	public Cinema savecinema(RequestCinema cinema);

	public List<Cinema> getAllCinema();

	public Cinema getCinemaById(long id);

	public Cinema updateCinema(long id, RequestCinema requestCinema);

	public void deleteCinema(long id);

	public List<Cinema> findByCinema(String name);

}
