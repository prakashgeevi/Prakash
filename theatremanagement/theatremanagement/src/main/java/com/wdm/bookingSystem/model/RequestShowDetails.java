package com.wdm.bookingSystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RequestShowDetails {

	@NotBlank(message = "showTime is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private String showTime;

	@NotBlank(message = "showdate is mandatory")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String date;

	@NotNull(message = "cinemaId is mandatory")
	private long cinemaId;

	@NotNull(message = "theatreId is mandatory")
	private long theatreId;

	@NotNull(message = "ticketPrice is mandatory")
	private Double ticketPrice;

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public RequestShowDetails(@NotNull String showTime, @NotNull String date, long cinemaId, long theatreId) {
		super();
		this.showTime = showTime;
		this.date = date;
		this.cinemaId = cinemaId;
		this.theatreId = theatreId;
	}

	public long getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(long theatreId) {
		this.theatreId = theatreId;
	}

	public long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

}
