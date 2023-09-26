package com.wdm.bookingSystem.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.EmailAlreadyInUseException;
import com.wdm.bookingSystem.exceptionhandler.NotFoundException;
import com.wdm.bookingSystem.exceptionhandler.UsernameAlreadyTakenException;
import com.wdm.bookingSystem.model.RequestLogin;
import com.wdm.bookingSystem.model.RequestUser;
import com.wdm.bookingSystem.model.ResponseGoogle;
import com.wdm.bookingSystem.payload.response.JwtResponse;
import com.wdm.bookingSystem.repository.UserRepository;
import com.wdm.bookingSystem.security.security.jwt.JwtUtils;
import com.wdm.bookingSystem.security.service.UserDetailsImpl;
import com.wdm.bookingSystem.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	IUserService iUserService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody RequestLogin loginRequest) {
		logger.info("Login User username={}", loginRequest.getUserName());

		System.out.println(loginRequest.getUserName());
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

		System.out.println(jwt);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), userDetails.getRole()));
	}

	@PostMapping("/signup")
	public User registerUser(@Valid @RequestBody RequestUser signUpRequest) {

		logger.info("User Register username={}", signUpRequest.getUserName());
		try {
			if (userRepository.existsByUserName(signUpRequest.getUserName())) {
				throw new UsernameAlreadyTakenException("Error: Username is already taken!");
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {

				throw new EmailAlreadyInUseException("Error: Email is already in use!");
			}

			User user = new User();
			user.setEmail(signUpRequest.getEmail());
			user.setMobile(signUpRequest.getMobile());
			user.setPassword(encoder.encode(signUpRequest.getPassword()));
			user.setRole(signUpRequest.getRole());
			user.setUserName(signUpRequest.getUserName());
			return userRepository.save(user);

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@PostMapping("/socialLogin")
	public ResponseEntity<?> socialLogin(@RequestBody ResponseGoogle ResponseGoogle) {

		User userAccount = userRepository.findByEmail(ResponseGoogle.getEmail());
		logger.info("Login With socialLogin", userAccount);
		if (userAccount != null) {

			String jwt = jwtUtils.generateTokenSocial(ResponseGoogle.getUserName(), ResponseGoogle.getEmail());

			return ResponseEntity.ok(new JwtResponse(jwt, userAccount.getId(), userAccount.getUserName(),
					userAccount.getEmail(), userAccount.getRole()));

		} else {
			RequestUser user = new RequestUser();

			user.setEmail(ResponseGoogle.getEmail());
			user.setUserName(ResponseGoogle.getUserName());
			user.setPassword(encoder.encode(ResponseGoogle.getPassword()));
			user.setRole("user");
			User registerUser = registerUser(user);
			String jwt = jwtUtils.generateTokenSocial(ResponseGoogle.getUserName(), ResponseGoogle.getEmail());

			return ResponseEntity.ok(new JwtResponse(jwt, registerUser.getId(), registerUser.getUserName(),
					registerUser.getEmail(), registerUser.getRole()));
		}

	}

}
