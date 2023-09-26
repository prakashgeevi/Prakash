package com.rentalapp.model;
 

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
 

@Data
@NoArgsConstructor
public class RequestUserAccount {
	
	@NotBlank(message = "User name cannot be blank")
	private String userName;
	
	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank")
	private String lastName;
	
	@NotBlank(message = "Email can't empty")
	@Email(message = "Email is not valid")
	private String emailId;
	
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 8)
	private String password;
	
	@NotBlank(message = "Street cannot be blank")
	private String street;
	
	@NotBlank(message = "City cannot be blank")
	private String city;
	
	@NotBlank(message = "Mobile number cannot be blank")
	private String mobileNumber;
	
	@NotBlank(message = "State cannot be blank")
	private String state;
	
	@NotBlank(message = "Country cannot be blank")
	private String country;
	
	@NotBlank(message = "Role cannot be blank")
	private String role;
	
	private String imageUrl;
}
