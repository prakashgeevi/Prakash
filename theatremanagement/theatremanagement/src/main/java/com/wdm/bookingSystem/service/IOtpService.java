package com.wdm.bookingSystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public interface IOtpService {

	public ResponseEntity<Object> getNewPassword(String email,String resetPassword);
	
	public ResponseEntity<Object> getOtp(String otp,String email);
	
	public ResponseEntity<Object> emailFind(@RequestParam("email") String email);

	
	
}
