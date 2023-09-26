package com.securecart.entity;

import java.util.HashSet; 
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cartId;

	@OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<Items> item = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "user", referencedColumnName = "userId")
	@JsonIgnore
	private User user;

	private String orderStatus;

	private double totalAmount;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Set<Items> getItem() {
		return item;
	}

	public void setItem(Set<Items> item) {
		this.item = item;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

 

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
