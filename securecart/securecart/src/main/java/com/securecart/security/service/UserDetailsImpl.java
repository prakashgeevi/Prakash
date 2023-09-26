package com.securecart.security.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.securecart.entity.User;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;
	private String lastName;

	private String email;

	@JsonIgnore
	private String password;

	private String role;

	private String status;

	private String profilePic;

	private Collection<? extends GrantedAuthority> authorities;
	
	  private String bankName;
	  private String bankAccountNumber;

	public UserDetailsImpl(Long id, String firstName, String lastName, String email, String password, String userRole,
			String status, String profilePic, String bankName, String bankAccountNumber) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.setRole(userRole);
		this.status = status;
		this.profilePic = profilePic;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
	}

	public static UserDetailsImpl build(User user) {

		return new UserDetailsImpl(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmailId(),
				user.getPassword(), user.getRole(), user.getStatus(), user.getProfilePicture(), user.getBankName(), user.getBankAccount());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	 

}
