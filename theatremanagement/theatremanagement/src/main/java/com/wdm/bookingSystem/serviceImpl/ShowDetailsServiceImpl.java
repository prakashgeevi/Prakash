package com.wdm.bookingSystem.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.Cinema;
import com.wdm.bookingSystem.entity.ShowDetails;
import com.wdm.bookingSystem.entity.Theatre;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.CinemaNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.IdNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.NotFoundException;
import com.wdm.bookingSystem.exceptionhandler.TheatreNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.UserNotAllowedException;
import com.wdm.bookingSystem.model.CinemaDTO;
import com.wdm.bookingSystem.model.RequestShowDetails;
import com.wdm.bookingSystem.model.ResponseShow;
import com.wdm.bookingSystem.model.ShowDTO;
import com.wdm.bookingSystem.model.TheaterDTO;
import com.wdm.bookingSystem.repository.ShowRepository;
import com.wdm.bookingSystem.repository.TheatreRepository;
import com.wdm.bookingSystem.service.IShowDetailsService;

@Service
public class ShowDetailsServiceImpl implements IShowDetailsService {

	@Autowired
	ShowRepository showRepository;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	TheatreServiceimpl theatreServiceimpl;

	@Autowired
	TheatreRepository theatreRepository;

	@Autowired
	CinemaServiceImpl cinemaServiceImpl;

	public ShowDetails getShowDetailsId(Long ShowId) {
		return showRepository.findById(ShowId).orElseThrow(() -> new CinemaNotFoundException("Cinema Id Not Fount"));
	}

	@Override
	public ShowDetails saveShowDetails(RequestShowDetails ShowDetails) {
		try {
			Theatre findById = theatreServiceimpl.getTheatreId(ShowDetails.getTheatreId());

			Cinema findBy = cinemaServiceImpl.getCinemaId(ShowDetails.getCinemaId());

			ShowDetails details = new ShowDetails();
			details.setDate(ShowDetails.getDate());
			details.setShowTime(ShowDetails.getShowTime());
			details.setTicketPrice(ShowDetails.getTicketPrice());
			details.setCinema(findBy);
			details.setTheatrename(findById);

			return showRepository.save(details);

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}

	}

	@Override
	public List<ShowDetails> getAllShowDetails() {

		return showRepository.findAll();
	}

	@Override
	public ShowDetails getShowDetailsById(long id) {
		try {
			ShowDetails obj = getShowDetailsId(id);
			return obj;
		} catch (Exception e) {
			System.out.println(e);

		}
		return null;

	}

	@Override
	public ShowDetails updateShowDetails(long id, RequestShowDetails requestShowDetails, long userid) {

		User findById = userServiceImpl.getUserById(userid);

		String getuserRoll = findById.getRole();
		if (getuserRoll.equalsIgnoreCase("admin")) {

			ShowDetails obj = getShowDetailsId(id);

			obj.setDate(requestShowDetails.getDate());
			obj.setShowTime(requestShowDetails.getShowTime());
			return obj;
		} else {
			throw new UserNotAllowedException("admin only allowed");
		}

	}

	@Override
	public void deleteShowDetails(long id) {

		Cinema findById = cinemaServiceImpl.getCinemaId(id);
		if (findById != null) {
			showRepository.deleteById(id);
		} else {
			throw new TheatreNotFoundException("Id not present");
		}

	}

	@Override
	public List<ShowDetails> getBytheatre(long id) {

		List<ShowDetails> obj = showRepository.findByTheatrename_id(id);

		return obj;
	}

	@Override
	public ResponseShow getBycinema(long id) {

		ResponseShow show = new ResponseShow();

		Cinema findById = cinemaServiceImpl.getCinemaId(id);

		if (findById != null) {
//		Cinema cinema = findById.getId();
			List<Theatre> theaters = theatreRepository.findAllByListOfShowCinemaId(id);
			for (Theatre t : theaters) {
				List<ShowDetails> collect = t.getListOfShow().stream().filter(e -> e.getCinema().getId() == id)
						.collect(Collectors.toList());
				t.setListOfShow(collect);
			}
			show.setCinemaId(findById.getId());
			show.setMovieName(findById.getMovieName());
			show.setLanguage(findById.getLanguage());
			show.setRating(findById.getRating());
			show.setDescription(findById.getDescription());
			show.setReleasedYear(findById.getReleasedYear());
			show.setPoster(findById.getPoster());
			show.setTheatres(theaters);

			return show;
		} else {
			throw new NotFoundException("Cinema not found");
		}
	}

	@Override
	public List<ShowDTO> getCinemaShowtimes(String date, long cinemaId) {

		List<ShowDetails> shows = showRepository.findByDateAndCinema_Id(date, cinemaId);

		List<ShowDTO> showDTOs = new ArrayList<>();

		for (ShowDetails show : shows) {

			ShowDTO showDTO = new ShowDTO();
			showDTO.setId(show.getId());
			showDTO.setShowTime(show.getShowTime());
			showDTO.setDate(show.getDate());
			showDTO.setTicketprice(show.getTicketPrice());

			Theatre theater = show.getTheatrename();
			TheaterDTO theaterDTO = new TheaterDTO();
			theaterDTO.setId(theater.getId());
			theaterDTO.setTheatreName(theater.getTheatrename());
			theaterDTO.setNumberOfRows(theater.getNumberofrows());
			theaterDTO.setNumberOfSeats(theater.getNumberofseats());
			showDTO.setTheatre(theaterDTO);

			Cinema cinema = show.getCinema();
			CinemaDTO cinemaDTO = new CinemaDTO();
			cinemaDTO.setMovieName(cinema.getMovieName());
			cinemaDTO.setId(cinema.getId());
			cinemaDTO.setDescription(cinema.getDescription());
			cinemaDTO.setLanguage(cinema.getLanguage());
			cinemaDTO.setPoster(cinema.getPoster());

			showDTO.setCinema(cinemaDTO);
			showDTOs.add(showDTO);
		}

		return showDTOs;

	}

}
