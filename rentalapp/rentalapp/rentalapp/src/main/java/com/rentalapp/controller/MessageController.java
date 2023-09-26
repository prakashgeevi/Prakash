package com.rentalapp.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentalapp.entity.PropertyMessages;
import com.rentalapp.model.RequestHostReply;
import com.rentalapp.model.RequestPropertyMessage;
import com.rentalapp.service.MessageService;

@RestController
@RequestMapping("/api/message")
@CrossOrigin("*")
public class MessageController {

	private static final Logger logger = LogManager.getLogger(MessageController.class);

	@Autowired
	MessageService messageService;

	@PostMapping
	public ResponseEntity<?> mapByConversion(@Valid @RequestBody RequestPropertyMessage message) {
		logger.info("message created successfully");
		return new ResponseEntity<PropertyMessages>(messageService.sendMessage(message), HttpStatus.CREATED);
	}
	
	@PostMapping("/hostreply")
	public ResponseEntity<?> mapByConversion(@Valid @RequestBody RequestHostReply message) {
		logger.info("message created successfully");
		return new ResponseEntity<PropertyMessages>(messageService.sendMessage(message), HttpStatus.CREATED);
	}

	@GetMapping("/from-id/{hostId}")
	public ResponseEntity<?> getAllMessage(@PathVariable("hostId") Long hostId) {
		logger.info("get message groupBy conversation id with to Id={}", hostId);
		Map<String, List<PropertyMessages>> messagesUser = messageService.getAllMessagesUser(hostId);
		logger.info("Get all message by conversation  successfully with total Size", messagesUser.size());
		return new ResponseEntity<>(messagesUser, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAllMessageByTenantIdAndPropertyId(@RequestParam("tenantId") Long tenantId,
			@RequestParam("propertyId") Long propertyId) {
		logger.info("Get all message  by tenantId={}, propertyId={}", tenantId, propertyId);
		List<PropertyMessages> propertyMessage = messageService.getAllMessageByTenantIdAndPropertyId(tenantId, propertyId);
		logger.info("Get all message by tenant and property successfully");
		return new ResponseEntity<>(propertyMessage, HttpStatus.OK);

	}
	@GetMapping("/conversationId")
	public ResponseEntity<?> getByMessageConversationId(@RequestParam("conversationId") String conversationId) {
		logger.info("Get all message  by comversationId={}, propertyId={}", conversationId);
		List<PropertyMessages> propertyMessage = messageService.getByMessageConversationId(conversationId);
		logger.info("Get all message by comversationId successfully");
		return new ResponseEntity<>(propertyMessage, HttpStatus.OK);

	}
	
	 
	@DeleteMapping
	public ResponseEntity<?> deleteQuestion(@PathVariable("tenantId") Long tenantId) {
		logger.info("Get All  message by tenantId and propertyId successfully");
		messageService.deleteMessageTenantUser(tenantId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/conversation/{conversationId}")
	public ResponseEntity<?> deleteByConversationId(@PathVariable("conversationId") String	conversationId) {
		logger.info("Get All  message by conversationId with={}",conversationId);
		messageService.deleteByConversationId(conversationId);
		logger.info("messages deleted successfully");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	

}
