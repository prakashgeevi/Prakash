package com.wdm.bookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.wdm.bookingSystem.entity.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmailAndPassword(String userName, String password);

	public User findByUserName(String userName);

	public User findByEmail(String email);
	
	public Boolean existsByUserName(String userName);

	public Boolean existsByEmail(String email);

	public User findByUserNameOrPassword(String userName, String password);

}
