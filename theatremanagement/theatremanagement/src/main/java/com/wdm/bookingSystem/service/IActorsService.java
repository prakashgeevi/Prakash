package com.wdm.bookingSystem.service;
import java.util.List;

import com.wdm.bookingSystem.entity.Actors;
import com.wdm.bookingSystem.model.RequestActors;
public interface IActorsService {
	
	public Actors saveActors(RequestActors actors);
	public List<Actors> getAllActors();

}
