package com.wdm.bookingSystem.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.Theatre;
import com.wdm.bookingSystem.model.RequestTheatre;

@Service
public interface ITheatreService {
	
	public Theatre saveTheatre(RequestTheatre theatre);

	public List<Theatre> getAllTheatre();

	public Theatre getTheatreById(long id);

	public Theatre updateTheatre(long id, RequestTheatre requestTheatre);

	public void deleteTheatre(long id);
	
	public List<Theatre> filterbyTheatreName(String tName);

}
