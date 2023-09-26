package com.securecart.controller;

import java.util.List;

import javax.validation.Valid;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securecart.entity.Questions;
import com.securecart.model.RequestQuestions;
import com.securecart.service.QuestionsService;

@RestController
@RequestMapping("/questions")
@CrossOrigin("*")
public class QuestionsController {
	
	
	@Autowired
	QuestionsService questionsService;
	
	private static final Logger logger = LogManager.getLogger(QuestionsController.class);
	
	@PostMapping
	public ResponseEntity<Questions> saveQuestions(@Valid @RequestBody RequestQuestions requestQuestions) {
		logger.info("Questions successfully");
		 return new ResponseEntity<Questions>(questionsService.saveQuestions(requestQuestions), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Questions>>getAllQuestions(@PathVariable("userId") Long userId  ) {
		logger.info("Questions successfully");
		 return new ResponseEntity<List<Questions>> (questionsService.getAllQuestions(userId), HttpStatus.OK);
	}
}
