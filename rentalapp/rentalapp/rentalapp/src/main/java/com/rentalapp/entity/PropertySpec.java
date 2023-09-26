package com.rentalapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PropertySpec {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean wirelessInternet;
	private boolean cooling;
	private boolean heating;
	private boolean kitchen;
	private boolean television;
	private boolean freeparking;
	private boolean ac;
	private boolean washingMachine; 
	private boolean hottub;
	private long maxNoOfPersonsAllowed;
	private int noOfBeds;
	private int noOfBedrooms;
	private int noOfBathrooms;

}
