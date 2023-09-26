package com.wdm.bookingSystem.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wdm.bookingSystem.entity.ShowDetails;
import com.wdm.bookingSystem.exceptionhandler.NotFoundException;
import com.wdm.bookingSystem.model.RequestShowDetails;
import com.wdm.bookingSystem.model.ResponseShow;
import com.wdm.bookingSystem.model.ShowDTO;
import com.wdm.bookingSystem.service.IShowDetailsService;

@RestController
@RequestMapping("/showDetails")
@CrossOrigin
public class ShowDetailsController {

	@Autowired
	IShowDetailsService ShowDetailsservice;

	private static final Logger logger = LoggerFactory.getLogger(TheatreController.class);

	@PostMapping
	public ResponseEntity<ShowDetails> saveShowDetails(@Valid @RequestBody RequestShowDetails ShowDetails) {
		logger.info("creat ShowDetails");
		return new ResponseEntity<>(ShowDetailsservice.saveShowDetails(ShowDetails), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ShowDetails>> getAllShowDetails() {

		List<ShowDetails> show = ShowDetailsservice.getAllShowDetails();

		// List<ResponseCimnema> showResponse = new ArrayList<>();

//		for(ShowDetails shows : show) {
//			ResponseCimnema responsecinema = new ResponseCimnema(shows.getCinema().getId(), shows.getId(), shows.getTheatrename().getId(),
//					shows.getCinema().getMovieName(), shows.getCinema().getRating(), shows.getCinema().getReleasedYear(),
//					shows.getCinema().getLanguage(), shows.getCinema().getPoster(), 
//					shows.getShowTime(), shows.getDate(), shows.getTheatrename().getTheatrename(), shows.getTheatrename().getNumberofrows(), shows.getTheatrename().getNumberofseats(),
//					shows.getTheatrename().getAddress().getStreet(), shows.getTheatrename().getAddress().getCity(),
//					shows.getTheatrename().getAddress().getState(), shows.getTheatrename().getAddress().getPincode(),
//					shows.getTheatrename().getAddress().getPincode());
//			
//			showResponse.add(responsecinema);
//		}

		logger.info("get a allShowDetails");
		return new ResponseEntity<>(show, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowDetails> getShowDetailsById(@Valid @PathVariable("id") long id) {
		logger.info("get a ShowDetails");

		ShowDetails theShowDetails = ShowDetailsservice.getShowDetailsById(id);

		if (theShowDetails == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(theShowDetails);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ShowDetails> updateShowDetails(@Valid @PathVariable long id,
			@RequestBody RequestShowDetails requestShowDetails, @RequestParam("user") long userid) {
		logger.info("update a ShowDetails");
		ShowDetails details = ShowDetailsservice.updateShowDetails(id, requestShowDetails, userid);

		if (details == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(details);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShowDetails(@Valid @PathVariable("id") long id) {
		logger.info("delete a ShowDetails");
		ShowDetailsservice.deleteShowDetails(id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@GetMapping("/theatreId/{id}")
	public ResponseEntity<?> loggingValidation(@PathVariable("id") long Id) {

		return new ResponseEntity<>(ShowDetailsservice.getBytheatre(Id), HttpStatus.OK);

	}

	@GetMapping("/cinemaId/{id}")
	public ResponseEntity<?> getByCinema(@PathVariable("id") long Id) {
		System.out.println("---------->");
		return new ResponseEntity<>(ShowDetailsservice.getBycinema(Id), HttpStatus.OK);

	}

	@GetMapping("/Date/{cinemaId}")
	public ResponseEntity<List<ShowDTO>> getMovieShowtimes(@RequestParam String date, @PathVariable long cinemaId) {

		List<ShowDTO> cinemaShowtimes = ShowDetailsservice.getCinemaShowtimes(date, cinemaId);

		return ResponseEntity.ok().body(cinemaShowtimes);

	}

}
