package com.wdm.bookingSystem.entity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bookingid")
	private long id;

	@OneToOne
	@JoinColumn(name = "theatreid")
	private Theatre theatre;

	@OneToOne
	private ShowDetails showdetails;

	@OneToOne
	@JoinColumn(name = "cinema")
	private Cinema cinema;

	@OneToMany(mappedBy = "booking", cascade = {CascadeType.PERSIST , CascadeType.MERGE})
	private List<BookingSeats> seats;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user", referencedColumnName = "userid")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transactionDetail", referencedColumnName = "transId")	
	private TransactionDetail transactionDetail;
	

	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public ShowDetails getShowdetails() {
		return showdetails;
	}

	public void setShowdetails(ShowDetails showdetails) {
		this.showdetails = showdetails;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public List<BookingSeats> getSeats() {
		return seats;
	}

	public void setSeats(List<BookingSeats> seats) {
		this.seats = seats;
	}


	/**
	 * @return the transactionDetail
	 */
	public TransactionDetail getTransactionDetail() {
		return transactionDetail;
	}


	/**
	 * @param transactionDetail the transactionDetail to set
	 */
	public void setTransactionDetail(TransactionDetail transactionDetail) {
		this.transactionDetail = transactionDetail;
	}





	 



}
