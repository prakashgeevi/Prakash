package com.wdm.bookingSystem.serviceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.Actors;
import com.wdm.bookingSystem.model.RequestActors;
import com.wdm.bookingSystem.repository.ActorsRepository;
import com.wdm.bookingSystem.service.IActorsService;

@Service
public class ActorsServiceImpl implements IActorsService{
	
	@Autowired
	ActorsRepository actorsRepository;

	@Override
	public Actors saveActors(RequestActors actors) {
		
		Actors obj = new Actors();
		obj.setActor(actors.getActor());
		obj.setActress(actors.getActress());
		obj.setDirecter(actors.getDirecter());
		obj.setMusicDirecter(actors.getMusicDirecter());
		obj.setReleasedYear(actors.getReleasedYear());
		return actorsRepository.save(obj);
	}

	@Override
	public List<Actors> getAllActors() {
		
		return actorsRepository.findAll();
	}

}
