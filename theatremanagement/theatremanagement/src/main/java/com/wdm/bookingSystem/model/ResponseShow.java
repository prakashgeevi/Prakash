package com.wdm.bookingSystem.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wdm.bookingSystem.entity.Theatre;

public class ResponseShow {
	
	@NotNull(message = "cinemaId is Mandatory")
	private long cinemaId;
	
	@NotBlank(message = "movieName is Mandatory")
	private String movieName;

	@NotNull(message = "rating is Mandatory")
	private Float rating;
	
	@NotBlank(message = "releasedYear is Mandatory")
	private String releasedYear;
	
	@NotBlank(message = "language is Mandatory")
	private String language;
	
	@NotBlank(message = "poster is Mandatory")
	private String poster;
	
	@NotBlank(message = "description is Mandatory")
	private String description;
	
	@NotBlank(message = "theatres is Mandatory")
	private List<Theatre> theatres;

	public long getCinemaId() {
		return cinemaId;
	}

	public String getMovieName() {
		return movieName;
	}

	public Float getRating() {
		return rating;
	}

	public String getReleasedYear() {
		return releasedYear;
	}

	public String getLanguage() {
		return language;
	}

	public String getPoster() {
		return poster;
	}

	public List<Theatre> getTheatres() {
		return theatres;
	}

	public void setCinemaId(long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public void setReleasedYear(String releasedYear) {
		this.releasedYear = releasedYear;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setTheatres(List<Theatre> theatres) {
		this.theatres = theatres;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
