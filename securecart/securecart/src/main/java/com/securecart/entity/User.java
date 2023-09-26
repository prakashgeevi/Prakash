package com.securecart.entity;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
@Table(name = "User")
//@Getter
//@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	private String firstName;

	private String lastName;
	 
	@Column(unique = true, nullable = false)
	private String emailId;

	
	private String password;

	private String role;
	
	private String status;
	
	private String street;
	private String city;
	private String state;
	private String country;
	
	private String bankName;
	
	private String bankAccount;
	
	@Lob
	private Blob profilePicture;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Orders> order;

	public String getProfilePicture() {
		try {
			if(profilePicture!=null && profilePicture.getBinaryStream()!=null){
			return Base64.getEncoder().encodeToString(profilePicture.
					getBinaryStream().readAllBytes());
			}
			else {
				return "";
			}
		} catch (IOException | SQLException e) {
			
			e.printStackTrace();
			return "";
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Orders> getOrder() {
		return order;
	}

	public void setOrder(List<Orders> order) {
		this.order = order;
	}

	public void setProfilePicture(Blob profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
}
