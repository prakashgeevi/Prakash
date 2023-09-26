package com.wdm.utils;

import java.security.SecureRandom;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class Utils {

//	@Autowired
//	
//	static JavaMailSender javaMailSender;
//	
//	
//	@Autowired
//	    SimpleMailMessage simpleMailMessage;
	

	public static String generateOTP() {
	        SecureRandom random = new SecureRandom();
	        return String.format("%06d", random.nextInt(999999));
	    }
	 
//	 public  void sendOtpByEmail(String email, String otp, String userName) throws MessagingException {
//		 
//		 	System.out.println(email);
//		 	System.out.println(otp);
//		 	System.out.println(userName);
//		 	
////		 	MimeMessage message = javaMailSender.createMimeMessage();
//		 	
//		 	MimeMessage message = javaMailSender.createMimeMessage();
//		 	
//			System.out.println(message);
//			
//			MimeMessageHelper helper = new MimeMessageHelper(message);
//			helper.setFrom("WavesCinema");
//			helper.setTo(email);
//			String subject = "Here's your One Time Password (OTP) - Expire in 10 minutes!";
//			String content = "<p>Hello " + userName + "</p>"
//					+ "<p>For security reason, you're required to use the following " + "One Time Password to login:</p>"
//					+ "<p><b>" + otp + "</b></p>" + "<br>" + "<p>Note: this OTP is set to expire in 10 minutes.</p>";
//			
//			helper.setSubject(subject);
//			helper.setText(content, true);
//			
//			javaMailSender.send(message);
//		}
//	   
	
}
