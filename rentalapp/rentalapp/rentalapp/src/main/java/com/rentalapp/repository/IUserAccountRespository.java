package com.rentalapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.User;

public interface IUserAccountRespository extends JpaRepository<User, Long> {

	public User findByEmailIdIgnoreCase(String emailId);

	public boolean existsByEmailIdIgnoreCase(String emailId);
	
	public boolean existsByUsernameIgnoreCase(String username);

	public List<User> findAllByStatus(String status);

	public List<User> findAllByRole(String role);

	public List<User> findAllByRoleNot(String lowerCase);

	public User findByIdAndRole(long userId, String string);

	public User findByUsernameIgnoreCase(String username);
}
