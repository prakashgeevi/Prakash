package com.wdm.bookingSystem.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.IdNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.NotFoundException;
import com.wdm.bookingSystem.exceptionhandler.UserNotAllowedException;
import com.wdm.bookingSystem.exceptionhandler.UserNotFoundException;
import com.wdm.bookingSystem.model.RequestUser;
import com.wdm.bookingSystem.repository.UserRepository;
import com.wdm.bookingSystem.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepository;


	public User getUserById(long userId) {
	        return userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException("User not found"));
	    }


	@Override
	public User updateUser(long id, RequestUser requestUser) {

		User theUser = getUserById(id);
		theUser.setEmail(requestUser.getUserName());
		theUser.setMobile(requestUser.getMobile());
		theUser.setRole(requestUser.getRole());
		theUser.setEmail(requestUser.getEmail());
		theUser.setPassword(requestUser.getPassword());
		return userRepository.save(theUser);
	}



	public User login(String userName, String password) {
		User user = userRepository.findByEmailAndPassword(userName, password);

		System.out.println(user);
		if (user == null) {
			throw new NotFoundException("invalid userName and password");
		}
		return user;

	}

}
