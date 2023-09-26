package com.securecart.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
@Entity
@Table
public class Replies {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String replyMessage;
	
	private LocalDateTime datetime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name = "questions_id")
	@JsonIgnore
	private Questions questions;
	

}
