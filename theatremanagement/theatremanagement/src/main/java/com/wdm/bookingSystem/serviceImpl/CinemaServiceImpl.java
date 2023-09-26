package com.wdm.bookingSystem.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wdm.bookingSystem.entity.Actors;
import com.wdm.bookingSystem.entity.Cinema;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.CinemaNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.IdNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.NotFoundException;
import com.wdm.bookingSystem.exceptionhandler.UserNotAllowedException;
import com.wdm.bookingSystem.model.RequestCinema;
import com.wdm.bookingSystem.repository.CinemaRepository;
import com.wdm.bookingSystem.service.ICinemaService;

@Service
public class CinemaServiceImpl implements ICinemaService {

	@Autowired
	CinemaRepository cinemaRepository;

	@Autowired
	UserServiceImpl userServiceImpl;

	public Cinema getCinemaId(long cinemaId) {
		return cinemaRepository.findById(cinemaId).orElseThrow(() -> new IdNotFoundException("User not found"));
	}

	@Override
	public Cinema savecinema(RequestCinema cinema) {

		try {

			Cinema obj = new Cinema();
			obj.setMovieName(cinema.getMovieName());
			obj.setLanguage(cinema.getLanguage());
			obj.setPoster(cinema.getPoster());
			obj.setDescription(cinema.getDescription());
			obj.setId(cinema.getUserid());
			Actors actors = new Actors();
			actors.setActor(cinema.getActor());
			actors.setActress(cinema.getActress());
			actors.setDirecter(cinema.getDirecter());
			actors.setMusicDirecter(cinema.getMusicDirecter());
			List<Actors> actorList = new ArrayList<>();

			actorList.add(actors);
			obj.setActors(actorList);

			return cinemaRepository.save(obj);
		}

		catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}

	}

	@Override
	public List<Cinema> getAllCinema() {
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema getCinemaById(long id) {
		Cinema cinemaId = getCinemaId(id);
		if (cinemaId != null) {
			return cinemaId;
		} else {
			throw new CinemaNotFoundException("Cinema Id Not Fount");
		}
	}

	@Override
	public Cinema updateCinema(long id, RequestCinema requestCinema) {
		User findById = userServiceImpl.getUserById(requestCinema.getUserid());
		String role = findById.getRole();
		if (role.equalsIgnoreCase("admin")) {
			Cinema obj = getCinemaId(id);
			obj.setMovieName(requestCinema.getMovieName());
			obj.setLanguage(requestCinema.getLanguage());

			obj.setPoster(requestCinema.getPoster());

			return cinemaRepository.save(obj);
		} else {
			throw new UserNotAllowedException("admin only allowed");
		}
	}

	@Override
	public void deleteCinema(long id) {
		Cinema findById = getCinemaId(id);
		if (findById != null) {
			cinemaRepository.deleteById(id);
		} else {
			throw new CinemaNotFoundException("Cinema Id Not Fount");
		}
	}

	@Override
	public List<Cinema> findByCinema(String name) {
		return cinemaRepository.findByMovieNameContaining(name);

	}

}
