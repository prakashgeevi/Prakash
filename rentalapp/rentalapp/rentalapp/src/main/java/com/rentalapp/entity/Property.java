package com.rentalapp.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "property")
public class Property {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type; //	private room, shared room, whole house	
	private double costPerDay;
	private String roomType; 
	private double space;
	@Column(length = 3000)
	private String floorAreaDesc;
	@Column(length = 3000)
	private String RentalRules;
	 
	private String street;
	private String city;
	private String state;
	private String country;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name; 
	
	private String status;
	private double overAllRating;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE ,CascadeType.REMOVE})
	@JoinColumn(name = "propertyspecId", referencedColumnName = "id")
 	private PropertySpec propertySpec;
	
	private String availableDays;
	
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE ,CascadeType.REMOVE})
	private List<PropertyImage> imagePath;
	
	 
	@OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE ,CascadeType.REMOVE})
	private List<Rating> ratings;

//	@OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE ,CascadeType.REMOVE})
//	private List<PropertyQuestions> propertyQuestions;
	
	
	@OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE ,CascadeType.REMOVE})
	@JsonIgnore
	private List<PropertySlots> propertySlot;
	
	@OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<PropertyReservation> propertyReservation;
	 
	
	@ManyToOne
    @JoinColumn(name = "hostId")
	private User user;


	public double getOverAllRating() {
		if(this.ratings != null) {
			List<Double> collect = this.ratings.stream().map(e -> e.getRating()).collect(Collectors.toList());
			return collect.stream().mapToDouble(a -> a).average().orElse(0);
		}
		return 0;
	}
	
	 
	 
	
}
