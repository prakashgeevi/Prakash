package com.wdm.bookingSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Bookingseats")
public class BookingSeats {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "seatnumber")
	private Integer seatNumber;

	@ManyToOne
	@JsonIgnore
	private Booking booking;

	public long getId() {
		return id;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
