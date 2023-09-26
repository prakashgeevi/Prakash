package com.securecart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String question;

	private LocalDateTime datetime;

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyerId;
	
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private User sellerId;

	@OneToMany(mappedBy = "questions")
	@OrderBy("datetime DESC")
	private List<Replies> replies = new ArrayList();

}
