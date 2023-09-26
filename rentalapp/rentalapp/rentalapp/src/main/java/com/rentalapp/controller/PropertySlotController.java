package com.rentalapp.controller;

import java.util.List;

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

import com.rentalapp.entity.PropertySlots;
import com.rentalapp.model.RequestSlots;
import com.rentalapp.service.PropertySlotsService;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin("*")
public class PropertySlotController {
	
	@Autowired
	PropertySlotsService propertySlotsService;

	private static final Logger logger = LogManager.getLogger(PropertySlotController.class);

	@PostMapping
	public ResponseEntity<?> saveQuestions(@Valid @RequestBody RequestSlots requestSlot) {
		logger.info("slots added");
		return new ResponseEntity<>(propertySlotsService.saveSlot(requestSlot),
				HttpStatus.CREATED);
	}
	
	@PutMapping("/{slotId}")
	public ResponseEntity<PropertySlots> updateQuestions(@Valid @RequestBody 
			RequestSlots requestSlot, @PathVariable("slotId") Long slotId) {
		logger.info("Questions successfully updated");
		 return new ResponseEntity<PropertySlots>(propertySlotsService.updateSlot(requestSlot, slotId), HttpStatus.OK);
	}

	
	@GetMapping("/{tenantId}")
	public ResponseEntity<List<PropertySlots>> getAllQuestions(@PathVariable("tenantId") Long tenantId) {
		logger.info("Get all Questions  successfully");
		return new ResponseEntity<List<PropertySlots>>(propertySlotsService.getAllSlots(tenantId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{slotId}/{userId}")
	public ResponseEntity<Void> deleteSlot(@PathVariable("slotId") Long slotId, @PathVariable("userId") Long userId) {
		logger.info("Questions deleted successfully");
			propertySlotsService.deleteBySlot(slotId, userId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
