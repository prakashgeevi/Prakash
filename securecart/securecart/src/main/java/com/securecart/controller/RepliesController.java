package com.securecart.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securecart.entity.Questions;
import com.securecart.model.RequestReplies;
import com.securecart.service.RepliesService;

@RestController
@RequestMapping("/replies")
@CrossOrigin("*")
public class RepliesController {

	private static final Logger logger = LogManager.getLogger(RepliesController.class);

	@Autowired
	RepliesService repliesService;

	@PostMapping
	public ResponseEntity<Questions> saveReplies(@Valid @RequestBody RequestReplies requestReplies) {
		logger.info("Replies successfully");
		return new ResponseEntity<Questions>(repliesService.saveReplies(requestReplies), HttpStatus.CREATED);
	}

}
