package com.wdm.bookingSystem.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wdm.bookingSystem.entity.Actors;
import com.wdm.bookingSystem.model.RequestActors;
import com.wdm.bookingSystem.service.IActorsService;

@RestController
@RequestMapping("/api/actors")
public class ActorsController {

	@Autowired
	IActorsService iActorsService;
	
	private static final Logger logger = LoggerFactory.getLogger(TheatreController.class);


	@PostMapping
	public ResponseEntity<Actors> saveActors(@Valid @RequestBody RequestActors actors) {
		logger.info("creat new Actors");
		
		Actors saveActors = iActorsService.saveActors(actors);

		logger.info("creat new Actors");
		return new ResponseEntity<>(saveActors, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Actors>> getAllActors() {
		logger.info("get a allActors");
		return new ResponseEntity<>(iActorsService.getAllActors(), HttpStatus.OK);
	}

}
