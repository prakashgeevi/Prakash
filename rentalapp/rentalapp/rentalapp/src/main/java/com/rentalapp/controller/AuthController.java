package com.rentalapp.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rentalapp.entity.User;
import com.rentalapp.model.RequestLogin;
import com.rentalapp.response.JwtResponse;
import com.rentalapp.response.MessageResponse;
import com.rentalapp.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	UserService userService;
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> registerUser(@RequestPart("userData") String resquestuser,
			@RequestPart(required = false, value = "profile") MultipartFile profile){
		User user = userService.registerUser(resquestuser, profile);
		logger.info("registered successfully");
		 return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> logingValidation(@Valid @RequestBody RequestLogin requst) throws Exception {
		return new ResponseEntity<JwtResponse>(userService.login(requst), HttpStatus.OK);
	}
	
	@GetMapping("/username/exists/{username}")
	public ResponseEntity<?> userNameExists(@PathVariable("username") String username) throws Exception {
		Boolean userNameExists = userService.userNameExists(username);
		return new ResponseEntity<MessageResponse>(new MessageResponse(userNameExists), HttpStatus.OK);
	}
	
	@GetMapping("/email/exists/{email}")
	public ResponseEntity<?> emailExists(@PathVariable("email") String email) throws Exception {
		Boolean emailExists = userService.emailExists(email);
		return new ResponseEntity<MessageResponse>(new MessageResponse(emailExists), HttpStatus.OK);
	}
}
