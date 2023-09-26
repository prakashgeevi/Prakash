package com.rentalapp.service;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalapp.constant.Role;
import com.rentalapp.constant.Status;
import com.rentalapp.entity.User;
import com.rentalapp.exception.IdNotFoundException;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.model.RequestLogin;
import com.rentalapp.model.RequestSocialLogin;
import com.rentalapp.model.RequestUserAccount;
import com.rentalapp.model.RequestUserApproval;
import com.rentalapp.repository.IOtpRepository;
import com.rentalapp.repository.IUserAccountRespository;
import com.rentalapp.response.JwtResponse;
import com.rentalapp.security.jwt.JwtUtils;
import com.rentalapp.security.service.UserDetailsImpl;
@Service
public class UserService implements ApplicationRunner {

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
	OtpService otpService;

	@Autowired
	ObjectMapper objectMapper;

	@Value("${rentalapp.app.admin.email}")
	private String adminEmail;

	@Value("${rentalapp.app.admin.password}")
	private String adminPassword;
	
	@Value("${rentalapp.app.admin.username}")
	private String adminUserName;

	@Value("${rentalapp.app.admin.firstName}")
	private String adminFname;

	@Value("${rentalapp.app.admin.lastName}")
	private String adminLname;

	public User registerUser(String requestuser, MultipartFile file) {
		try {
			RequestUserAccount signUpRequest = objectMapper.readValue(requestuser, RequestUserAccount.class);
			if (userRepo.existsByEmailIdIgnoreCase(signUpRequest.getEmailId())) {
				throw new IdNotFoundException("Error: Email is already in use!");
			}
			User user = new User();
			user.setEmailId(signUpRequest.getEmailId());
			user.setUsername(signUpRequest.getUserName());
			user.setFirstName(signUpRequest.getFirstName());
			user.setLastName(signUpRequest.getLastName());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			user.setRole(signUpRequest.getRole().toLowerCase());
			user.setMobileNumber(signUpRequest.getMobileNumber());
			user.setCity(signUpRequest.getCity());
			user.setCountry(signUpRequest.getCountry());
			user.setState(signUpRequest.getState());
			user.setStreet(signUpRequest.getStreet());
			user.setCreatedAt(LocalDateTime.now());
			
			//if role has host orelse any role approved
			
			if(signUpRequest.getRole().equalsIgnoreCase("tenant")) {
				user.setStatus(Status.APPROVED.name());
			}else {
				user.setStatus(Status.PENDING.name());
			}
			
			
			
			user.setImageUrl(signUpRequest.getImageUrl());
			if (file != null) {	
				Blob blob = new SerialBlob(file.getBytes());
				user.setProfilePicture(blob);
			}
			return userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
	}

	public JwtResponse login(RequestLogin requst) throws Exception {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(requst.getUserName(), requst.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			if(!userDetails.getStatus().equals("APPROVED") && userDetails.getRole().equals("host")) {
				throw new IdNotFoundException("Your account is still not yet approved");
			}
			String jwt = jwtutils.generateJwtToken(authentication);
			
			return new JwtResponse(jwt, userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(),
					userDetails.getUsername(),
					userDetails.getEmail(), userDetails.getRole(), userDetails.getStatus(),
					userDetails.getProfilePic());
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
				otpService.saveOtpForEmail(user.getEmailId(), user.getFirstName());
			} else {
				throw new NotFoundException("Email id not found");
			}
			return requst.getUserName();
		} catch (Exception e) {
			throw new IdNotFoundException(e.getMessage());
		}
	}
 
	public JwtResponse socialLogin(RequestSocialLogin socialLogin) throws Exception {
		User reqUser = userRepo.findByEmailIdIgnoreCase(socialLogin.getEmail());
		if (reqUser != null) {
			String jwt = jwtutils.generateTokenSocial(socialLogin.getEmail());
			return new JwtResponse(jwt, reqUser.getUserId(), reqUser.getFirstName(), reqUser.getLastName(),reqUser.getUsername(),
					reqUser.getEmailId(), reqUser.getRole(), reqUser.getStatus(), reqUser.getProfilePicture());
		}
		else {
			RequestUserAccount reqUser1 = new RequestUserAccount();
			reqUser1.setEmailId(socialLogin.getEmail());
			reqUser1.setFirstName(socialLogin.getFirstName());
			reqUser1.setLastName(socialLogin.getLastName());
			reqUser1.setRole(socialLogin.getRole());
			reqUser1.setPassword(passwordEncoder.encode(socialLogin.getPassword()));
			reqUser1.setImageUrl(socialLogin.getImageUrl());
			MultipartFile file = null; 
			User registerUser = registerUser(objectMapper.writeValueAsString(reqUser1), file);
			String jwt = jwtutils.generateTokenSocial(socialLogin.getEmail());
			return new JwtResponse(jwt, registerUser.getUserId(), registerUser.getFirstName(),
					registerUser.getLastName(),registerUser.getUsername(), registerUser.getEmailId(), registerUser.getRole(), registerUser.getStatus(), registerUser.getProfilePicture());
		} 
	}
	

	 
	public User editUser(String requestuser, MultipartFile file, Long userId) {
		try {

			RequestUserAccount signUpRequest = objectMapper.readValue(requestuser, RequestUserAccount.class);
			User updateUser = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
			if (!signUpRequest.getUserName().equalsIgnoreCase(updateUser.getUsername())
					&& userRepo.existsByUsernameIgnoreCase(signUpRequest.getUserName())) {
				throw new IdNotFoundException("Error: Username is already in use!");
			}
			if (!signUpRequest.getEmailId().equalsIgnoreCase(updateUser.getEmailId())
					&& userRepo.existsByEmailIdIgnoreCase(signUpRequest.getEmailId())) {
				throw new IdNotFoundException("Error: Email is already in use!");
			}
			updateUser.setEmailId(signUpRequest.getEmailId());
			updateUser.setUsername(signUpRequest.getUserName());
			if(signUpRequest.getPassword()!= null && !signUpRequest.getPassword().equals("")) {
				updateUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			}
			updateUser.setMobileNumber(signUpRequest.getMobileNumber());
			updateUser.setFirstName(signUpRequest.getFirstName());
			updateUser.setLastName(signUpRequest.getLastName());
			updateUser.setCity(signUpRequest.getCity());
			updateUser.setCountry(signUpRequest.getCountry());
			updateUser.setState(signUpRequest.getState());
			updateUser.setStreet(signUpRequest.getStreet());
			if (file != null) {
				Blob blob = new SerialBlob(file.getBytes());
				updateUser.setProfilePicture(blob);
			}
			return userRepo.save(updateUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
	}
	
	public List<User> getAllPendingUser() {
		return userRepo.findAllByRoleNot(Role.ADMIN.name().toLowerCase());
	}
	
	public User getUserById(long id) {
		User user = userRepo.findById(id).get();
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		return user;
	}
	
	public List<User> getAllSellers() {
		return userRepo.findAllByRole(Role.HOST.name().toLowerCase());
	}
	
	public User setStatusApprove(RequestUserApproval requestUserApprovel) {
		User user = userRepo.findById(requestUserApprovel.getUserId())
				.orElseThrow(() -> new NotFoundException("user is not found"));
		user.setStatus(requestUserApprovel.getStatus());
		return userRepo.save(user);
	}
	
	public JwtResponse verifytwostepotp(String email, String otp) {
		 otpService.checkOTPValid(otp, email);
		User reqUser = userRepo.findByEmailIdIgnoreCase(email);
		if (reqUser != null) {
			String jwt = jwtutils.generateTokenSocial(email);
			String encodeToString=reqUser.getProfilePicture();
			return new JwtResponse(jwt, reqUser.getUserId(), reqUser.getFirstName(), reqUser.getLastName(),reqUser.getUsername(),
					reqUser.getEmailId(), reqUser.getRole(), reqUser.getStatus(), encodeToString);
		} else {
			return null;
		}
	}
	
	public void run(ApplicationArguments args) throws Exception {
		User adminUser = new User();
		if (userRepo.findByEmailIdIgnoreCase(adminEmail) == null) {
			adminUser.setFirstName(adminFname);
			adminUser.setLastName(adminLname);
			adminUser.setEmailId(adminEmail);
			adminUser.setUsername(adminUserName);
			adminUser.setPassword(passwordEncoder.encode(adminPassword));
			adminUser.setRole(Role.ADMIN.name().toLowerCase());
			adminUser.setStreet("37-39 Falirou St");
			adminUser.setCity("Athens");
			adminUser.setState("Attica");
		  	adminUser.setCountry("Greece");
			adminUser.setStatus(Status.APPROVED.name());
			adminUser.setCreatedAt(LocalDateTime.now());
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

	public Boolean userNameExists(String username) {
		User reqUser = userRepo.findByUsernameIgnoreCase(username);
		return reqUser!=null;
	}
	
	public Boolean emailExists(String email) {
		User reqUser = userRepo.findByEmailIdIgnoreCase(email);
		return reqUser!=null;
	}
}
