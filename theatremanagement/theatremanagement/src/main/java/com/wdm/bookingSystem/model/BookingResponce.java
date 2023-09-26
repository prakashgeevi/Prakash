package com.wdm.bookingSystem.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wdm.bookingSystem.entity.BookingSeats;

public class BookingResponce {

	@NotBlank(message = "poster is mandatory")
	private String poster;

	@NotBlank(message = "movieName is mandatory")
	private String movieName;

	@NotBlank(message = "theatreName is mandatory")
	private String theatreName;

	@NotBlank(message = "state is mandatory")
	private String state;

	@NotBlank(message = "city is mandatory")
	private String city;

	@NotNull(message = "number is mandatory")
	private long number;

	@NotBlank(message = "showDate is mandatory")
	private String showDate;

	@NotBlank(message = "showTime is mandatory")
	private String showTime;

	@NotNull(message = "seat Number is mandatory")
	private List<BookingSeats> seatNo;

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getPoster() {
		return poster;
	}

	public String getMovieName() {
		return movieName;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public long getNumber() {
		return number;
	}

	public String getShowDate() {
		return showDate;
	}

	public List<BookingSeats> getSeatNo() {
		return seatNo;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public void setSeatNo(List<BookingSeats> seatNo) {
		this.seatNo = seatNo;
	}

}
