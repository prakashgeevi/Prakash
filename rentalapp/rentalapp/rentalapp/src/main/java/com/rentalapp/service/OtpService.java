package com.rentalapp.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.rentalapp.entity.PasswordResetOtp;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.repository.IOtpRepository;

@Service
public class OtpService {
	
	@Autowired
	IOtpRepository otpRepo;

	@Autowired
	JavaMailSender javaMailSender;
	
	public void saveOtpForEmail(String email, String firstName) {
		try {
		String otp = generateOTP();
		PasswordResetOtp passwordOtp = otpRepo.findByEmail(email);
		if(passwordOtp==null) {
			passwordOtp = new PasswordResetOtp();
		}
			passwordOtp.setEmail(email);
			passwordOtp.setOneTimePassword(otp);
			passwordOtp.setExpiryTime(LocalDateTime.now());
			otpRepo.save(passwordOtp);
			sendOtpByEmail(email, otp, firstName);
			
		 
		}catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
	}
	
	public String checkOTPValid(String otp, String email) {
		if(otpRepo.existsByEmailIgnoreCase(email)) {
			PasswordResetOtp resetOtp = otpRepo.findByOneTimePasswordAndEmail(otp, email);
			long minutes = ChronoUnit.MINUTES.between(resetOtp.getExpiryTime(), LocalDateTime.now());
			if (minutes > 10) {
				return "OTP has expired or enter the recent otp";
			}
			return "Otp is Valid..!";
		}
			
		else {
			throw new NotFoundException("InValid Otp");
		}
	
	}
	
	
	
	
	
	
	
	
	private String generateOTP() {
		SecureRandom random = new SecureRandom();
		return String.format("%06d", random.nextInt(999999));
	}
	
	
	private void sendOtpByEmail(String email, String otp, String userName)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("SecureCart@shopme.com", "Secure cart");
		helper.setTo(email);
		String subject = "Here's your One Time Password (OTP) - Expire in 10 minutes!";
		String content = "<p>Hello " + userName + "</p>"
				+ "<p>For security reason, you're required to use the following " + "One Time Password to login:</p>"
				+ "<p><b>" + otp + "</b></p>" + "<br>" + "<p>Note: this OTP is set to expire in 10 minutes.</p>";
		helper.setSubject(subject);
		helper.setText(content, true);
		javaMailSender.send(message);

	}
}
