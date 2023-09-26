package com.wdm.bookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wdm.bookingSystem.entity.OneTimePassword;

public interface OtpRepository extends JpaRepository<OneTimePassword, Long> {

	OneTimePassword findByOtp(String otp);
	
	

}
