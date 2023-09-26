//package com.rentalapp.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.rentalapp.entity.PropertyQuestions;
//import com.rentalapp.model.RequestQuestions;
//import com.rentalapp.service.QuestionsService;
//
//@RestController
//@RequestMapping("/api/questions")
//@CrossOrigin("*")
//public class PropertyQuestionController {
//
//	@Autowired
//	QuestionsService questionsService;
//
//	private static final Logger logger = LogManager.getLogger(PropertyQuestionController.class);
//
//	@PostMapping
//	public ResponseEntity<PropertyQuestions> saveQuestions(@Valid @RequestBody RequestQuestions requestQuestions) {
//		logger.info("Questions successfully");
//		return new ResponseEntity<PropertyQuestions>(questionsService.saveQuestions(requestQuestions),
//				HttpStatus.CREATED);
//	}
//
//	@PutMapping("/{questionId}")
//	public ResponseEntity<PropertyQuestions> updateQuestions(@Valid @RequestBody 
//				RequestQuestions requestQuestions, @PathVariable("questionId") Long questionId) {
//		logger.info("Questions successfully");
//		 return new ResponseEntity<PropertyQuestions>(questionsService.updateQuestion(requestQuestions, questionId), HttpStatus.OK);
//	}
//
//	@GetMapping("/{propertId}")
//	public ResponseEntity<List<PropertyQuestions>> getAllQuestions(@PathVariable("propertId") Long propertId) {
//		logger.info("Get all Questions  successfully");
//		return new ResponseEntity<List<PropertyQuestions>>(questionsService.getAllQuestions(propertId), HttpStatus.OK);
//	}
//	
//	@DeleteMapping
//	public ResponseEntity<Void> deleteQuestion(@PathVariable("questionId") Long questionId) {
//		logger.info("Questions deleted successfully");
//			questionsService.deleteByQuestion(questionId);
//		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//	}
//	
//
//}
