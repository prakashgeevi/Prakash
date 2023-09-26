package com.rentalapp.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.web.bind.annotation.RestController;

import com.rentalapp.entity.Property;
import com.rentalapp.model.RequestSlots;
import com.rentalapp.service.PropertySlotsService;

@RestController
@RequestMapping("/api/propertyslot")
@CrossOrigin("*")
public class PropertSlotsController {
	
	@Autowired
	PropertySlotsService slotService;

	private static final Logger logger = LogManager.getLogger(PropertSlotsController.class);
 
	
	@PostMapping
	public ResponseEntity<?> saveQuestions(@Valid @RequestBody RequestSlots requestQuestions) {
		logger.info("Slot created successfully");
		Property propertySlots = slotService.saveSlot(requestQuestions);
		return new ResponseEntity<>(propertySlots,HttpStatus.CREATED);
		
		
	}

	@PutMapping("/{questionId}")
	public ResponseEntity<?> updateQuestions(@Valid @RequestBody 
			RequestSlots slotRequest, @PathVariable("questionId") Long questionId) {
		logger.info("slot updated successfully");
		 return new ResponseEntity<>(slotService.updateSlot(slotRequest, questionId), HttpStatus.OK);
	}

	@GetMapping("/{propertId}")
	public ResponseEntity<?> getAllQuestions(@PathVariable("propertId") Long propertId) {
		logger.info("Get all Questions  successfully");
		return new ResponseEntity<>(slotService.getAllSlots(propertId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{slotId}/{propertyId}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable("slotId") Long slotId, @PathVariable("propertyId") Long propertyId) {
		logger.info("Questions deleted successfully");
			slotService.deleteBySlot(slotId, propertyId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
