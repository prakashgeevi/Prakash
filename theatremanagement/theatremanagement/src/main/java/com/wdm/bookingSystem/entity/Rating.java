package com.wdm.bookingSystem.entity;
//package com.example.demo.entity;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "movie rating")
//public class Rating {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private long ratingId;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "user", referencedColumnName = "USER_id")
//	private User user;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "cinema", referencedColumnName = "cinema_id")
//	private Cinema cinema;
//	
//	private double rating;
//
//	
//	
//	
//	/**
//	 * @return the ratingId
//	 */
//	public long getRatingId() {
//		return ratingId;
//	}
//
//	/**
//	 * @return the user
//	 */
//	public User getUser() {
//		return user;
//	}
//
//	/**
//	 * @return the cinema
//	 */
//	public Cinema getCinema() {
//		return cinema;
//	}
//
//	/**
//	 * @return the rating
//	 */
//	public double getRating() {
//		return rating;
//	}
//
//	/**
//	 * @param ratingId the ratingId to set
//	 */
//	public void setRatingId(long ratingId) {
//		this.ratingId = ratingId;
//	}
//
//	/**
//	 * @param user the user to set
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	/**
//	 * @param cinema the cinema to set
//	 */
//	public void setCinema(Cinema cinema) {
//		this.cinema = cinema;
//	}
//
//	/**
//	 * @param rating the rating to set
//	 */
//	public void setRating(double rating) {
//		this.rating = rating;
//	}
//	
//	
//	
//
//}
