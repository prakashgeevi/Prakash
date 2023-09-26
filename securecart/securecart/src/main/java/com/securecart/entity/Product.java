package com.securecart.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = "productName") })
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "productId")
	private Long productId;

	@Column(name = "productname", nullable = false, unique = true)
	private String productName;
	
	@Column(name = "description", nullable = false, unique = true)
	private String description;

	@Column(name = "stocks")
	private Long stocks;

	@Column(name = "unit")
	private String unit;

	@Column(name = "price")
	private double price;

	@Column(name = "addedAt")
	private LocalDateTime addedAt;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "sellerId")
	private User seller;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@Column(length=10000000)
	private byte[] imageData;
	
	@OneToMany(mappedBy ="product" , cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Feedback> feedbacks;
	 
	@OneToMany(mappedBy ="product" , cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Ratings> ratings;
	
	private String status;
	 
}
