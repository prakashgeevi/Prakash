package com.wdm.bookingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OneTimePassword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "otp")
	private String otp;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "expiryTime")
	private LocalDateTime expiryTime;

	 
	public Long getId() {
		return id;
	}

	 
	public String getOtp() {
		return otp;
	}

	 
	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

 
	public void setId(Long id) {
		this.id = id;
	}

	 
	public void setOtp(String otp) {
		this.otp = otp;
	}
 
	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
