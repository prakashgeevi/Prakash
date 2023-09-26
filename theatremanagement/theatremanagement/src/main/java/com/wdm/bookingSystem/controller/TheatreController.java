package com.wdm.bookingSystem.controller;

import java.util.List;


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

import com.wdm.bookingSystem.entity.Theatre;
import com.wdm.bookingSystem.model.RequestTheatre;
import com.wdm.bookingSystem.service.ITheatreService;


@RestController
@RequestMapping("/Theatre")
@CrossOrigin
public class TheatreController {

	@Autowired
	ITheatreService iTheatreService;

	private static final Logger logger = LoggerFactory.getLogger(TheatreController.class);

	@PostMapping
	public ResponseEntity<Theatre> saveTheatre(@RequestBody RequestTheatre theatre) {
		logger.info("save new theater - Theatrename={},", theatre.getTheatrename());
		return new ResponseEntity<>(iTheatreService.saveTheatre(theatre), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity <List<Theatre>> getAllTheatre() {
		 List<Theatre> allTheatre = iTheatreService.getAllTheatre();
		 logger.info("get a alltheatre", allTheatre.size());
		 return new ResponseEntity<>(allTheatre, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Theatre> getTheatreById(@PathVariable("id") long id) {
		logger.info("get a theatre");
		Theatre theTheatre = iTheatreService.getTheatreById(id);

		if (theTheatre == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(theTheatre);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Theatre> updateTheatre(@PathVariable long id, @RequestBody RequestTheatre requestTheatre
			) {
		
		Theatre theate = iTheatreService.updateTheatre(id, requestTheatre);
		logger.info("update a theatre",theate);
		if (theate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(theate);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTheatre(@PathVariable("id") long id) {
		iTheatreService.deleteTheatre(id);
		logger.info("deleteTheatre theateId ={} ",id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/searchbytheatrename")
	public ResponseEntity<List<Theatre>> getTheatreByName(@RequestParam("theatrename") String name) {
		
		List<Theatre> theTheatre = iTheatreService.filterbyTheatreName(name);
		logger.info("searchbytheatrename Theatre={}",theTheatre);
		
		if (theTheatre == null) {
			return ResponseEntity.notFound().build();
		}
			return ResponseEntity.ok().body(theTheatre);
	}

}
