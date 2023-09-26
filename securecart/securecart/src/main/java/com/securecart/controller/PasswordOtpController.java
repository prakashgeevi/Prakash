package com.securecart.controller;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securecart.entity.PasswordResetOtp;
import com.securecart.entity.User;
import com.securecart.exception.ProductCustomException;
import com.securecart.repository.IOtpRepository;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.response.MessageResponse;

@RestController
@RequestMapping("/forget-password")
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
	JavaMailSender javaMailSender;

	@PostMapping
	public ResponseEntity<Object> findByEmail(@Valid @RequestParam("email") String email) {
		try {
			User user = UserRepo.findByEmailIdIgnoreCase(email);
			logger.info("user details email={}", email);
			if (user != null) {
				String otp = generateOTP();
				PasswordResetOtp passwordOtp = new PasswordResetOtp();
				passwordOtp.setEmail(email);
				passwordOtp.setOneTimePassword(otp);
				passwordOtp.setExpiryTime(LocalDateTime.now());
				otpRepo.save(passwordOtp);
				sendOtpByEmail(user.getEmailId(), otp, user.getFirstName());
			} else {
				throw new ProductCustomException("Email id not found");
			}
		} catch (Exception e) {
			throw new ProductCustomException(e.getMessage());
		}
		return ResponseEntity.ok(new MessageResponse("otp send your email id..!"));

	}

	@PostMapping("/otp")
	public ResponseEntity<Object> getOneTimePassword(@RequestParam("otp") String otp,
			@RequestParam("email") String email) {
		logger.info("user details for confirm password email={} ", email);
		List<PasswordResetOtp> objectList = otpRepo.findByEmail(email);
		LocalDateTime recentTime = objectList.stream().map(PasswordResetOtp::getExpiryTime).collect(Collectors.toList())
				.stream().max(LocalDateTime::compareTo).orElse(null);
		System.out.println(recentTime);
		PasswordResetOtp resetOtp = otpRepo.findByOneTimePasswordAndEmailAndExpiryTime(otp, email, recentTime);
		if (resetOtp == null) {
			throw new ProductCustomException("InValid Otp");
		}
		long minutes = ChronoUnit.MINUTES.between(resetOtp.getExpiryTime(), LocalDateTime.now());
		if (minutes > 10) {
			return ResponseEntity.badRequest().body("OTP has expired or enter the recent otp");
		}
		return ResponseEntity.ok(new MessageResponse("Otp is Valid..!"));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Object> getResetPassword(@RequestParam("email") String email,
			@RequestParam("password") String resetPassword) {
		User user = UserRepo.findByEmailIdIgnoreCase(email);
		user.setPassword(passwordEncoder.encode(resetPassword));
		UserRepo.save(user);
		return ResponseEntity.ok(new MessageResponse("New password changed..!"));
	}

	public String generateOTP() {
		SecureRandom random = new SecureRandom();
		return String.format("%06d", random.nextInt(999999));
	}

	public void sendOtpByEmail(String email, String otp, String userName)
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
