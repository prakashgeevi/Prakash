package com.wdm.bookingSystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class RequestCinema {

	@NotBlank(message = "movieName is mandatory")
//	@JsonProperty("movieName")
	private String movieName;



	@NotBlank(message = "language is mandatory")
	private String language;

	@NotBlank(message = "poster is mandatory")
	private String poster;

	@NotBlank(message = "description is mandatory")
	private String description;

	@NotNull(message = "userid is mandatory")
	private long userid;

	@Size(min = 2, max = 40)
	@NotBlank(message = "actor is mandatory")
	private String actor;

	@Size(min = 2, max = 40)
	@NotBlank(message = "actress is mandatory")
	private String actress;

	@Size(min = 2, max = 40)
	@NotBlank(message = "musicDirecter is mandatory")
	private String musicDirecter;

	@Size(min = 2, max = 40)
	@NotBlank(message = "Directer is mandatory")
	private String Directer;

	// private List<Long> actors;

	public RequestCinema() {
		super();

	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}



	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	/**
	 * @return the actor
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * @return the actress
	 */
	public String getActress() {
		return actress;
	}

	/**
	 * @return the musicDirecter
	 */
	public String getMusicDirecter() {
		return musicDirecter;
	}

	/**
	 * @return the directer
	 */
	public String getDirecter() {
		return Directer;
	}

	/**
	 * @param actor the actor to set
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}

	/**
	 * @param actress the actress to set
	 */
	public void setActress(String actress) {
		this.actress = actress;
	}

	public void setMusicDirecter(String musicDirecter) {
		this.musicDirecter = musicDirecter;
	}

	public void setDirecter(String directer) {
		Directer = directer;
	}

	public RequestCinema(@Size(min = 2, max = 15) String actor, @Size(min = 2, max = 15) String actress,
			@Size(min = 2, max = 15) String musicDirecter, @Size(min = 2, max = 15) String directer) {
		super();
		this.actor = actor;
		this.actress = actress;
		this.musicDirecter = musicDirecter;
		Directer = directer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
