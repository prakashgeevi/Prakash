package com.rentalapp.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "propertySlot")
public class PropertySlots {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	 
	private LocalDate date;
	
	
	@ManyToOne
	@JoinColumn(name = "propertyId")
	@JsonIgnore
	private Property property;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "reservationId")
	@JsonIgnore
	private PropertyReservation propertyReservation;
	 
}
