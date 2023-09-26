package com.wdm.bookingSystem.serviceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestParam;

import com.wdm.bookingSystem.entity.OneTimePassword;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.NotFoundException;
import com.wdm.bookingSystem.payload.response.MessageResponse;
import com.wdm.bookingSystem.repository.OtpRepository;
import com.wdm.bookingSystem.repository.UserRepository;
import com.wdm.bookingSystem.service.IOtpService;
import com.wdm.utils.Utils;

@Service
public class OtpServiceImpl implements IOtpService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	OtpRepository otpRepo;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	PasswordEncoder passwordEncoder;
	
//	@Autowired
//	Utils Utils;
	
	public  void sendOtpByEmail(String email, String otp, String userName) throws MessagingException {

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("WavesCinema");

			helper.setTo(email);

			String subject = "Here's your One Time Password (OTP) - Expire in 10 minutes!";
			String content = "<p>Hello " + userName + "</p>"
					+ "<p>For security reason, you're required to use the following " + "One Time Password to login:</p>"
					+ "<p><b>" + otp + "</b></p>" + "<br>" + "<p>Note: this OTP is set to expire in 10 minutes.</p>";

			helper.setSubject(subject);
			helper.setText(content, true);

			javaMailSender.send(message);

		}
	 

	public ResponseEntity<Object> emailFind(@RequestParam("email") String email) {

		try {

			User user = userRepo.findByEmail(email);

			if (user != null) {
				String otp = Utils.generateOTP();
				System.out.println(otp);
				OneTimePassword passwordOtp = new OneTimePassword();
				passwordOtp.setOtp(otp);
				passwordOtp.setExpiryTime(LocalDateTime.now());
				passwordOtp.setEmail(email);
				otpRepo.save(passwordOtp);
				
////				Utils.sendOtpByEmail(email, otp, otp);
//				Utils utils = new Utils();
//				
//				utils.sendOtpByEmail(email, otp, user.getUserName());
				
			sendOtpByEmail(user.getEmail(), otp, user.getUserName());

			}
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	
	public ResponseEntity<Object> getOtp(String otp, String email) {

		User user = userRepo.findByEmail(email);

		OneTimePassword passwordOtp = otpRepo.findByOtp(otp);

		if (passwordOtp == null) {
			throw new NotFoundException("InValid Otp");
		}

		LocalDateTime createdAt = passwordOtp.getExpiryTime();
		LocalDateTime now = LocalDateTime.now();
		long minutes = ChronoUnit.MINUTES.between(createdAt, now);

		if (minutes > 10) {
			return ResponseEntity.badRequest().body("OTP has expired");
		}

		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<Object> getNewPassword(String email, String resetPassword) {

		User userAccount = userRepo.findByEmail(email);
		userAccount.setPassword(passwordEncoder.encode(resetPassword));
		userRepo.save(userAccount);
		return ResponseEntity.ok(new MessageResponse("New password changed..!"));

	}

}
