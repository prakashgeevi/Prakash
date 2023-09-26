package com.securecart.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.securecart.entity.User;
import com.securecart.model.RequestLogin;
import com.securecart.model.RequestSocialLogin;
import com.securecart.model.RequestUserApproval;
import com.securecart.response.JwtResponse;

@Service
public interface UserService {
	
	
	
	public User registerUser(String signUpRequest, MultipartFile file);
	
	public User editUser(String signUpRequest, MultipartFile file, Long userId);
	
	public JwtResponse login(RequestLogin requst) throws Exception;
	
	public JwtResponse socialLogin(RequestSocialLogin socialLogin) throws Exception;
	
	public List<User> getAllPendingUser();
	
	
	public User setStatusApprove(RequestUserApproval requestUserApproval,long id);

	public List<User> getAllSellers();

	public JwtResponse verifytwostepotp(String email, String otp);

	public String twoStepVerify(@Valid RequestLogin requst);

	public User getUserById(long id);
	
	
	
	
	
	
	
	

//	public UserAccount saveuser(RequestUserAccount user);
//
//	public void delete(long id) throws Exception;
//	
//	public UserAccount updateUser(RequestUserAccount user, long id);
//	
//	public List<UserAccount> getAlluser();
//	
//	 public UserResponse getuserId(long id);
//	
//	public UserAccount getuserbyEmail(String email, String password) throws Exception;

}
