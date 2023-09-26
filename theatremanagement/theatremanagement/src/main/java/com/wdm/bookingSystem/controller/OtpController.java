package com.wdm.bookingSystem.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wdm.bookingSystem.service.IOtpService;

@RestController
@RequestMapping("/forget-paswd")
@CrossOrigin
public class OtpController {

//	@Autowired
//	UserRepository userRepo;
//
//	@Autowired
//	OtpRepository otpRepo;
//
//	@Autowired
//	JavaMailSender javaMailSender;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
	@Autowired
	IOtpService iOtpService;

	private static final Logger logger = LoggerFactory.getLogger(OtpController.class);

	@PostMapping
	public ResponseEntity<Object> findByEmail(@RequestParam("email") String email) {

		return ResponseEntity.ok().body(iOtpService.emailFind(email));
	}

//	private String generateOTP() {
//		SecureRandom random = new SecureRandom();
//		return String.format("%06d", random.nextInt(999999));
//	}

//	private void sendOtpByEmail(String email, String otp, String userName) throws MessagingException {
//
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		helper.setFrom("WavesCinema");
//
//		helper.setTo(email);
//
//		String subject = "Here's your One Time Password (OTP) - Expire in 10 minutes!";
//		String content = "<p>Hello " + userName + "</p>"
//				+ "<p>For security reason, you're required to use the following " + "One Time Password to login:</p>"
//				+ "<p><b>" + otp + "</b></p>" + "<br>" + "<p>Note: this OTP is set to expire in 10 minutes.</p>";
//
//		helper.setSubject(subject);
//		helper.setText(content, true);
//
//		javaMailSender.send(message);
//
//	}

	@PostMapping("/otp")
	public ResponseEntity<Object> getOneTimePassword(@RequestParam("otp") String otp,
			@RequestParam("email") String email) {
		return ResponseEntity.ok().body(iOtpService.getOtp(otp, email));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Object> getResetPassword(@RequestParam("email") String email,
			@RequestParam("password") String resetPassword) {
		logger.info("ResetPassword email={},password={}", email,resetPassword);	
		return ResponseEntity.ok().body(iOtpService.getNewPassword(email, resetPassword));	
		
	}

}
