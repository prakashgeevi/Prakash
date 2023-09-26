package com.wdm.bookingSystem.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CinemaDTO {

	@NotNull(message = "Cinema Id is mandatory")
	private Long id;

	@NotBlank(message = "movieName is mandatory")
	private String movieName;

	@NotNull(message = "rating is mandatory")
	private Float rating;

	@NotBlank(message = "releasedYear is mandatory")
	private String releasedYear;

	@NotBlank(message = "language is mandatory")
	private String language;

	@NotBlank(message = "poster is mandatory")
	private String poster;

	@NotBlank(message = "description is mandatory")
	private String description;

	private List<TheaterDTO> theaters;

	public Long getId() {
		return id;
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

	public String getDescription() {
		return description;
	}

	public List<TheaterDTO> getTheaters() {
		return theaters;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTheaters(List<TheaterDTO> theaters) {
		this.theaters = theaters;
	}
}
