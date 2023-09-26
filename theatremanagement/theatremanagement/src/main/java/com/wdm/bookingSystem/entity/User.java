package com.wdm.bookingSystem.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "role")
	private String role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}

	

	public String getPassword() {
		return password;
	}


	
	public void setPassword(String password) {
		this.password = password;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	public String getRole() {
		return role;
	}

	
	public void setRole(String role) {
		this.role = role;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public User(long id, String userName, String email, String password, String mobile, String role) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.role = role;
	}


	public User(String userName, String email, String password) {
		
		this.userName = userName;
		this.email = email;
		this.password = password;
	}


	public User() {
		// TODO Auto-generated constructor stub
	}

	




	




}
