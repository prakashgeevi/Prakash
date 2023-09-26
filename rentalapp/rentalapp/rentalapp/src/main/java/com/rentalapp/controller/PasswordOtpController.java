package com.rentalapp.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentalapp.entity.User;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.repository.IOtpRepository;
import com.rentalapp.repository.IUserAccountRespository;
import com.rentalapp.response.MessageResponse;
import com.rentalapp.service.OtpService;

@RestController
@RequestMapping("/api/forget-password")
@CrossOrigin("*")
public class PasswordOtpController {

	private static final Logger logger = LogManager.getLogger(PasswordOtpController.class);

	@Autowired
	IOtpRepository otpRepo;

	@Autowired
	IUserAccountRespository UserRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	OtpService otpService;

	@PostMapping
	public ResponseEntity<Object> findByEmail(@Valid @RequestParam("email") String email) {
		try {
			User user = UserRepo.findByEmailIdIgnoreCase(email);
			if (user != null) {
				otpService.saveOtpForEmail(email, user.getFirstName());
			} else {
				throw new NotFoundException("Email id not found");
			}
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok(new MessageResponse("otp send your email id..!"));

	}

	@PostMapping("/otp")
	public ResponseEntity<Object> getOneTimePassword(@RequestParam("otp") String otp,
			@RequestParam("email") String email) {
		logger.info("user details for confirm password email={} ", email);
		return new ResponseEntity<Object>(otpService.checkOTPValid(otp, email), HttpStatus.OK);
		
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Object> getResetPassword(@RequestParam("email") String email,
			@RequestParam("password") String resetPassword) {
		User user = UserRepo.findByEmailIdIgnoreCase(email);
		user.setPassword(passwordEncoder.encode(resetPassword));
		UserRepo.save(user);
		return ResponseEntity.ok(new MessageResponse("New password changed..!"));
	}

	 



}
