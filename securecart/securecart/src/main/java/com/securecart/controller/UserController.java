package com.securecart.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.securecart.entity.User;
import com.securecart.model.RequestLogin;
import com.securecart.model.RequestSocialLogin;
import com.securecart.model.RequestUserApproval;
import com.securecart.response.JwtResponse;
import com.securecart.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserService userService;
	private static final Logger logger = LogManager.getLogger(UserController.class);

	
	@PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> registerUser(@RequestPart("userData") String resquestuser,
			@RequestPart(value = "profile", required = false) MultipartFile profile){
		User user = userService.registerUser(resquestuser, profile);
		logger.info("registered successfully");
		 return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	

	
	@PostMapping("/twoStepVerification")
	public ResponseEntity<?> twoStepVerification(@Valid @RequestBody RequestLogin requst) throws Exception {
		return new ResponseEntity<String>(userService.twoStepVerify(requst), HttpStatus.CREATED);
	}
	
	@PostMapping("/verifytwostepotp")
	public ResponseEntity<?> verifytwostepotp(@RequestParam("otp") String otp,
			@RequestParam("email") String email) throws Exception {
		return new ResponseEntity<JwtResponse>(userService.verifytwostepotp(email, otp), HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> loggingValidation(@Valid @RequestBody RequestLogin requst) throws Exception {
		return new ResponseEntity<JwtResponse>(userService.login(requst), HttpStatus.CREATED);
	}

	@PostMapping("/socialLogin")
	public ResponseEntity<?> socialLogin(@Valid @RequestBody RequestSocialLogin socialLogin) throws Exception {
		 return new ResponseEntity<JwtResponse>(userService.socialLogin(socialLogin), HttpStatus.OK);
	}
	
	
	@GetMapping("/allusers")
	public ResponseEntity<?> getAllPendingAccount() throws Exception {
		 return new ResponseEntity<List<User>>(userService.getAllPendingUser(), HttpStatus.OK);
	}
	
	@GetMapping("/sellers")
	public ResponseEntity<?> getAllSellers() throws Exception {
		 return new ResponseEntity<List<User>>(userService.getAllSellers(), HttpStatus.OK);
	}
	
	@PutMapping("/status/{id}")
	public ResponseEntity<?> setUserApprove(@RequestBody RequestUserApproval RequestUserApproval
			,@PathVariable("id") long id){
		 return new ResponseEntity<User>(userService.setStatusApprove( RequestUserApproval, id), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateUser(@Valid @RequestPart("updateUserdata") String resquestuser,
			@RequestPart(value = "updateprofile", required = false) MultipartFile file, @PathVariable("id") long id)
			throws Exception {
		logger.info("update User - resquestuser= {}, file={} ", resquestuser);
		return new ResponseEntity<User>(userService.editUser(resquestuser, file, id), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getByid(@PathVariable("id") long id) {
		logger.info("getUserById  UserId : " + id);
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);

	}
	
}
