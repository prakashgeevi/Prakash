package com.securecart.repository;
 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securecart.entity.User;
 

public interface IUserAccountRespository extends JpaRepository<User, Long>{
	
	public User findByEmailIdIgnoreCase(String emailId);
	public boolean existsByEmailIdIgnoreCase(String emailId);
	public List<User> findAllByStatus(String status);
	public User findByUserIdAndRole(Long userId, String string);
	public User findByUserId(Long userId);
	public List<User> findAllByRole(String role);
	public List<User> findAllByRoleNot(String lowerCase);
}
