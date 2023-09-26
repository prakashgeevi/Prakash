package com.securecart.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securecart.constant.Role;
import com.securecart.constant.Status;
import com.securecart.entity.PasswordResetOtp;
import com.securecart.entity.User;
import com.securecart.exception.IdNotFoundException;
import com.securecart.exception.ProductCustomException;
import com.securecart.exception.UserNotFoundException;
import com.securecart.model.RequestLogin;
import com.securecart.model.RequestSocialLogin;
import com.securecart.model.RequestUserAccount;
import com.securecart.model.RequestUserApproval;
import com.securecart.repository.IOtpRepository;
import com.securecart.repository.IUserAccountRespository;
import com.securecart.response.JwtResponse;
import com.securecart.security.jwt.JwtUtils;
import com.securecart.security.service.UserDetailsImpl;
import com.securecart.service.ProductService;
import com.securecart.service.UserService;

@Service
public class UserServiceimpl implements UserService, ApplicationRunner {

	@Autowired
	IUserAccountRespository userRepo;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtutils;

	@Autowired
	IOtpRepository otpRepo;

	@Autowired
	IUserAccountRespository UserRepo;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProductService productservice;

	@Value("${securecart.app.admin.email}")
	private String adminEmail;

	@Value("${securecart.app.admin.password}")
	private String adminPassword;

	@Value("${securecart.app.admin.firstName}")
	private String adminFname;

	@Value("${securecart.app.admin.lastName}")
	private String adminLname;

	public User registerUser(String requestuser, MultipartFile file) {
		try {
			RequestUserAccount signUpRequest = objectMapper.readValue(requestuser, RequestUserAccount.class);

			if (userRepo.existsByEmailIdIgnoreCase(signUpRequest.getEmailId())) {
				throw new IdNotFoundException("Error: Email is already in use!");
			}
			User user = new User();
			user.setEmailId(signUpRequest.getEmailId());
			user.setFirstName(signUpRequest.getFirstName());
			user.setLastName(signUpRequest.getLastName());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			user.setRole(signUpRequest.getRole().toLowerCase());
			user.setCity(signUpRequest.getCity());
			user.setCountry(signUpRequest.getCountry());
			user.setState(signUpRequest.getState());
			user.setStreet(signUpRequest.getStreet());
			user.setStatus(Status.PENDING.name());
			user.setBankAccount(signUpRequest.getBankAccountNumber());
			user.setBankName(signUpRequest.getBankName());
			if (file != null) {	
				
				Blob blob = new SerialBlob(file.getBytes());
				user.setProfilePicture(blob);
			}
			
			return userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNotFoundException(e.getMessage());
		}

	}
	
	public User registerUser(RequestUserAccount signUpRequest, MultipartFile file) {
		try {
			if (userRepo.existsByEmailIdIgnoreCase(signUpRequest.getEmailId())) {
				throw new IdNotFoundException("Error: Email is already in use!");
			}
			User user = new User();
			user.setEmailId(signUpRequest.getEmailId());
			user.setFirstName(signUpRequest.getFirstName());
			user.setLastName(signUpRequest.getLastName());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			user.setRole(signUpRequest.getRole().toLowerCase());
			user.setCity(signUpRequest.getCity());
			user.setCountry(signUpRequest.getCountry());
			user.setState(signUpRequest.getState());
			user.setStreet(signUpRequest.getStreet());
			user.setStatus(Status.PENDING.name());
			user.setBankAccount(signUpRequest.getBankAccountNumber());
			user.setBankName(signUpRequest.getBankName());
			if (file != null) {	
				
				Blob blob = new SerialBlob(file.getBytes());
				user.setProfilePicture(blob);
			}
			
			return userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNotFoundException(e.getMessage());
		}

	}

	public JwtResponse login(RequestLogin requst) throws Exception {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(requst.getUserName(), requst.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtutils.generateJwtToken(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			return new JwtResponse(jwt, userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(),
					userDetails.getEmail(), userDetails.getRole(), userDetails.getStatus(),
					userDetails.getProfilePic(), userDetails.getBankName(), userDetails.getBankAccountNumber());

		} catch (Exception e) {
			throw new IdNotFoundException(e.getMessage());
		}
	}

	public String twoStepVerify(RequestLogin requst) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(requst.getUserName(), requst.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			User user = userRepo.findByEmailIdIgnoreCase(requst.getUserName());
			if (user != null) {
				String otp = generateOTP();
				PasswordResetOtp passwordOtp = new PasswordResetOtp();
				passwordOtp.setEmail(requst.getUserName());
				passwordOtp.setOneTimePassword(otp);
				passwordOtp.setExpiryTime(LocalDateTime.now());
				otpRepo.save(passwordOtp);
				sendOtpByEmail(user.getEmailId(), otp, user.getFirstName());
			} else {
				throw new ProductCustomException("Email id not found");
			}
			return requst.getUserName();

		} catch (Exception e) {
			throw new IdNotFoundException(e.getMessage());
		}
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

	public JwtResponse socialLogin(RequestSocialLogin socialLogin) throws Exception {
		User reqUser = userRepo.findByEmailIdIgnoreCase(socialLogin.getEmail());
		if (reqUser != null) {
			String jwt = jwtutils.generateTokenSocial(socialLogin.getEmail());
			System.out.println(jwt);
			return new JwtResponse(jwt, reqUser.getUserId(), reqUser.getFirstName(), reqUser.getLastName(),
					reqUser.getEmailId(), reqUser.getRole(), reqUser.getStatus(), reqUser.getProfilePicture(), reqUser.getBankName(), reqUser.getBankAccount());

		}

		else {
			RequestUserAccount reqUser1 = new RequestUserAccount();
			reqUser1.setEmailId(socialLogin.getEmail());
			reqUser1.setFirstName(socialLogin.getFirstName());
			reqUser1.setLastName(socialLogin.getLastName());
			reqUser1.setRole(socialLogin.getRole());
			reqUser1.setPassword(passwordEncoder.encode(socialLogin.getPassword()));
			User registerUser = registerUser(reqUser1, null);
			String jwt = jwtutils.generateTokenSocial(socialLogin.getEmail());
			return new JwtResponse(jwt, registerUser.getUserId(), registerUser.getFirstName(),
					registerUser.getLastName(), registerUser.getEmailId(), registerUser.getRole(), registerUser.getStatus()
					, registerUser.getProfilePicture(), null, null);
		}

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User adminUser = new User();
		if (userRepo.findByEmailIdIgnoreCase(adminEmail) == null) {

			adminUser.setFirstName(adminFname);
			adminUser.setLastName(adminLname);
			adminUser.setEmailId(adminEmail);
			adminUser.setPassword(passwordEncoder.encode(adminPassword));
			adminUser.setRole(Role.ADMIN.name().toLowerCase());
			adminUser.setStreet("10Th Floor Bgn Bank Pembangunan Jln Sultan Ismail");
			adminUser.setCity("Kuala Lumpur");
			adminUser.setState("Wilayah Persekutuan");
			adminUser.setCountry("Malaysia");
			adminUser.setStatus(Status.APPROVED.name());
			userRepo.save(adminUser);
		}

//		if (userRepo.findByEmailIdIgnoreCase("admin2@gmail.com") == null) {
//			User adminUser2 = new User();
//			adminUser2.setPassword(passwordEncoder.encode("admin@222"));
//			adminUser2.setFirstName("admin2");
//			adminUser2.setLastName("admin2");
//			adminUser2.setEmailId("admin2@gmail.com");
//			adminUser2.setRole(Role.SELLER.name().toLowerCase());
//			adminUser2.setStreet("Jalan 20/16A, Paramount Garden");
//			adminUser2.setCity("Petaling Jaya");
//			adminUser2.setState("Selangor");
//			adminUser2.setCountry("Malaysia");
//			adminUser2.setStatus(Status.APPROVED.name());
//			userRepo.save(adminUser2);
//		}

	}

	@Override
	public List<User> getAllPendingUser() {
		return userRepo.findAllByRoleNot(Role.ADMIN.name().toLowerCase());
	}

	@Override
	public List<User> getAllSellers() {
		return userRepo.findAllByRole(Role.SELLER.name().toLowerCase());
	}

	@Override
	public User setStatusApprove(RequestUserApproval requestUserApprovel, long id) {
		
		User user = userRepo.findById(requestUserApprovel.getUserId())
				.orElseThrow(() -> new UserNotFoundException("user is not found"));
		if(requestUserApprovel.getStatus().equals("APPROVED")) {
			user.setStatus(requestUserApprovel.getStatus());
			return userRepo.save(user);
		} else {
			userRepo.delete(user);
			return null;
		}
	}

	@Override
	public JwtResponse verifytwostepotp(String email, String otp) {
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
			throw new UserNotFoundException("OTP has expired or enter the recent otp");
		}

		User reqUser = userRepo.findByEmailIdIgnoreCase(email);
		
		
		if (reqUser != null) {
			String jwt = jwtutils.generateTokenSocial(email);
			
			System.out.println(jwt);
			String encodeToString=reqUser.getProfilePicture();
			
			return new JwtResponse(jwt, reqUser.getUserId(), reqUser.getFirstName(), reqUser.getLastName(),
					reqUser.getEmailId(), reqUser.getRole(), reqUser.getStatus(), encodeToString, reqUser.getBankName(), reqUser.getBankAccount());

		} else {
			return null;
		}
	}

	@Override
	public User editUser(String requestuser, MultipartFile file, Long userId) {
		try {

			RequestUserAccount signUpRequest = objectMapper.readValue(requestuser, RequestUserAccount.class);
			User updateUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

			if (!signUpRequest.getEmailId().equalsIgnoreCase(updateUser.getEmailId())
					&& userRepo.existsByEmailIdIgnoreCase(signUpRequest.getEmailId())) {
				throw new IdNotFoundException("Error: Email is already in use!");
			}

			updateUser.setEmailId(signUpRequest.getEmailId());
			updateUser.setFirstName(signUpRequest.getFirstName());
			updateUser.setLastName(signUpRequest.getLastName());

			updateUser.setCity(signUpRequest.getCity());
			updateUser.setCountry(signUpRequest.getCountry());
			updateUser.setState(signUpRequest.getState());
			updateUser.setStreet(signUpRequest.getStreet());
			updateUser.setBankAccount(signUpRequest.getBankAccountNumber());
			updateUser.setBankName(signUpRequest.getBankName());

			if (file != null) {
				
				Blob blob = new SerialBlob(file.getBytes());
				updateUser.setProfilePicture(blob);
			}

			return userRepo.save(updateUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNotFoundException(e.getMessage());
		}

	}

	@Override
	public User getUserById(long id) {
		User user = userRepo.findByUserId(id);
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}

		return user;
	}

}
