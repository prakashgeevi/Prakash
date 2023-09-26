package com.rentalapp.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rentalapp.repository.Interaction;

import lombok.Data;

@Entity
@Table(name = "rating")
@Data
public class Rating implements Interaction{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private double rating;
	
	@Column(length = 3000)
	private String review;
	
	private LocalDateTime ratedAt;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "propertyId")
	@JsonIgnore
	private Property property;

}
