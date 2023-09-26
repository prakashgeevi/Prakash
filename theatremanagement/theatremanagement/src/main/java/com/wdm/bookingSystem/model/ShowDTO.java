package com.wdm.bookingSystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ShowDTO {

	@NotNull(message = "ShowId is Mandatory")
	private Long id;

	@NotBlank(message = "showTime is Mandatory")
	private String showTime;

	@NotBlank(message = "date is Mandatory")
	private String date;

	@NotNull(message = "ticketprice is Mandatory")
	private Double ticketprice;
	@NotNull(message = "cinema is Mandatory")
	private CinemaDTO cinema;
	
	@NotNull(message = "theatre is Mandatory")
	private TheaterDTO theatre;

	public Long getId() {
		return id;
	}

	public String getShowTime() {
		return showTime;
	}

	public String getDate() {
		return date;
	}

	public CinemaDTO getCinema() {
		return cinema;
	}

	public TheaterDTO getTheatre() {
		return theatre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCinema(CinemaDTO cinema) {
		this.cinema = cinema;
	}

	public void setTheatre(TheaterDTO theatre) {
		this.theatre = theatre;
	}

	public Double getTicketprice() {
		return ticketprice;
	}

	public void setTicketprice(Double ticketprice) {
		this.ticketprice = ticketprice;
	}

}
