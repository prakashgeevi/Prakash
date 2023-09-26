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
import com.wdm.bookingSystem.entity.Cinema;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.IdNotFoundException;
import com.wdm.bookingSystem.model.RequestCinema;
import com.wdm.bookingSystem.service.ICinemaService;

@RestController
@RequestMapping("/cinema")
@CrossOrigin
public class CinemaController {

	@Autowired
	ICinemaService iCinemaService;
	


	private static final Logger logger = LoggerFactory.getLogger(CinemaController.class);

	@PostMapping
	public ResponseEntity<Cinema> savecinema(@RequestBody RequestCinema cinema) {
		logger.info("creat Cinema");
		Cinema thacinema = iCinemaService.savecinema(cinema);
		return ResponseEntity.ok().body(thacinema);
	}

	@GetMapping
	public ResponseEntity<List<Cinema>> getAllCinema() {
		List<Cinema> allCinema = iCinemaService.getAllCinema();
		logger.info("get a allCinema", allCinema.size());
		return new ResponseEntity<>(allCinema, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cinema> getCinemaById(@Valid @PathVariable("id") long id) {

		Cinema cinemaById = iCinemaService.getCinemaById(id);
		logger.info("get By Cinema Id", cinemaById);
		return new ResponseEntity<Cinema>(cinemaById, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cinema> updateCinema(@Valid @PathVariable long id, @RequestBody RequestCinema requestCinema) {

		Cinema newcinema = iCinemaService.updateCinema(id, requestCinema);
		logger.info("updated cinema Id", newcinema);

		if (newcinema == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(newcinema);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCinema(@Valid @PathVariable("id") long id) {
		iCinemaService.deleteCinema(id);
		logger.info("delete a theatre", id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@GetMapping("/searchbycinemaname")
	public ResponseEntity<List<Cinema>> getCinemaByName(@RequestParam("movieName") String name) {
		logger.info("search cinema name");

		List<Cinema> theCinema = iCinemaService.findByCinema(name);

		if (theCinema == null) {
			return ResponseEntity.notFound().build();
		}
		logger.info("get a theatre", theCinema.size());
		return ResponseEntity.ok().body(theCinema);

	}

}
