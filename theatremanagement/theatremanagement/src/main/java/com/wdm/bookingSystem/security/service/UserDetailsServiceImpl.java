package com.wdm.bookingSystem.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + email);
		}
		
		return UserDetailsImpl.build(user);
	}
}
