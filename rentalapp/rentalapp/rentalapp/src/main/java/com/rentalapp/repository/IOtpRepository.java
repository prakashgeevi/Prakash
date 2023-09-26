package com.rentalapp.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalapp.entity.PasswordResetOtp;

 

@Repository
public interface IOtpRepository extends JpaRepository<PasswordResetOtp, Long> {
	
	public PasswordResetOtp findByOneTimePasswordAndEmail(String otp, String email);
	
	public boolean existsByEmailIgnoreCase(String emailId);
	 
	
	public PasswordResetOtp findByEmail(String email);

	
 

}
