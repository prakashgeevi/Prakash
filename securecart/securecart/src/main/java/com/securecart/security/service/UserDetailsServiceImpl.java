package com.securecart.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securecart.entity.User;
import com.securecart.exception.IdNotFoundException;
import com.securecart.repository.IUserAccountRespository;
 
 

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  IUserAccountRespository userRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
	   	
	 User user = userRepo.findByEmailIdIgnoreCase(emailId);
	  
	 if(user==null) {
		 
		 throw new IdNotFoundException("Email Id not found");
	 	}
	 
	  
     
    return UserDetailsImpl.build(user);
  }

}
