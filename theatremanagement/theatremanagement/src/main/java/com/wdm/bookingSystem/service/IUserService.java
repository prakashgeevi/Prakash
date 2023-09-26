package com.wdm.bookingSystem.service;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.model.RequestUser;

public interface IUserService {

	public User updateUser(long id, RequestUser requestUser);

	public User login(String userName, String password);
}
