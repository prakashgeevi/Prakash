package com.wdm.bookingSystem.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TheaterDTO {

	@NotNull(message = "Theater id is Mandatory")
	private Long id;

	@NotBlank(message = "theatreName id is Mandatory")
	private String theatreName;

	@NotNull(message = "numberOfSeats id is Mandatory")
	private long numberOfSeats;

	@NotNull(message = "numberOfSeats id is Mandatory")
	private long numberOfRows;

	private List<ShowDTO> listOfShow;
//	    private AddressDTO address;

	public Long getId() {
		return id;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public long getNumberOfSeats() {
		return numberOfSeats;
	}

	public long getNumberOfRows() {
		return numberOfRows;
	}

	public List<ShowDTO> getListOfShow() {
		return listOfShow;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public void setNumberOfSeats(long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public void setNumberOfRows(long l) {
		this.numberOfRows = l;
	}

	public void setListOfShow(List<ShowDTO> listOfShow) {
		this.listOfShow = listOfShow;
	}

}
