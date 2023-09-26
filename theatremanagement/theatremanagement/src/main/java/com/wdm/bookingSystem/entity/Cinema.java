package com.wdm.bookingSystem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cinema")

public class Cinema {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cinemaid")
	private long id;
	
	@Column(name = "moviename")
	private String movieName;
	
	@Column(name = "rating")
	private Float rating;
	
	@Column(name = "releasedyear")
	private String releasedYear;

	@Column(name = "language")
	private String language;
	
	@Column(name = "poster")
	private String poster;
	
	@Column(name = "description")
	private String description;
	
	
	
	public String getDescription() {
		return description;
	}


	
	public void setDescription(String description) {
		this.description = description;
	}


	public String getPoster() {
		return poster;
	}

	
	public void setPoster(String poster) {
		this.poster = poster;
	}

	@ManyToMany( cascade = {
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	@JoinTable(name = "Actors_Cinema_Map", joinColumns = { @JoinColumn(name = "cinema_id") }, inverseJoinColumns = {
	@JoinColumn(name = "ACTORS_ID") })
	private List<Actors> actors;

	public List<Actors> getActors() {
		return actors;
	}

	public void setActors(List<Actors> actors) {
		this.actors = actors;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getReleasedYear() {
		return releasedYear;
	}

	public void setReleasedYear(String releasedYear) {
		this.releasedYear = releasedYear;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
