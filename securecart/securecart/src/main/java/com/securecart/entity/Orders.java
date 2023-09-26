package com.securecart.entity;

 
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 

 

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderid")
	private Long orderId;

	
	@Column(name = "ordertime")
	private LocalDateTime dateTime = LocalDateTime.now();

	@Column(name = "totalAmount")
	private double totalAmount;

	@OneToOne
	@JoinColumn(name = "cartId", referencedColumnName = "cartId")
	private Cart cart;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "userId")
	
	private User user;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderPayid", referencedColumnName = "orderPayid")
	private OrderPayment orderPayment;
	
	
 

}
