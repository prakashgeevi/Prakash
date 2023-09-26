package com.wdm.bookingSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "transactiondetail")
public class TransactionDetail {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "transid")
		private long transId;
		
		@Column(name = "orderid")
		private String orderId;
		
		@Column(name = "bookingtime")
		private String bookingTime;
		
	
		@Column(name = "amount")
		private double amount;
		
		@Column(name = "amounttype")
		private String amountType;
		
		@Column(name = "status")
		private String status;
		
		@Column(name = "payercountry")
		private String payerCountry;
		
		@Column(name = "payeremail")
		private String payerEmail;
		
		@Column(name = "payerfullname")
		private String payerFullName;
		
		@Column(name = "payerid")
		private String payerid;
		
		@Column(name = "payeeemail")
		private String payeeEmail;
		
		@Column(name = "merchantid")
		private String merchantId;
		
		public long getTransId() {
			return transId;
		}

		
		public String getOrderId() {
			return orderId;
		}

		
		public String getBookingTime() {
			return bookingTime;
		}



		
	

		
	

		
		public double getAmount() {
			return amount;
		}

	
		public String getAmountType() {
			return amountType;
		}

		
		public String getStatus() {
			return status;
		}

		
		public String getPayerCountry() {
			return payerCountry;
		}

	
		public String getPayerEmail() {
			return payerEmail;
		}

	
		public String getPayerFullName() {
			return payerFullName;
		}

		
		public String getPayerid() {
			return payerid;
		}

		
		public String getPayeeEmail() {
			return payeeEmail;
		}

		
		public String getMerchantId() {
			return merchantId;
		}

		
		public void setTransId(long transId) {
			this.transId = transId;
		}

	
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

	
		public void setBookingTime(String bookingTime) {
			this.bookingTime = bookingTime;
		}

	
		public void setAmount(double amount) {
			this.amount = amount;
		}

	
		public void setAmountType(String amountType) {
			this.amountType = amountType;
		}

		
		public void setStatus(String status) {
			this.status = status;
		}

	
		public void setPayerCountry(String payerCountry) {
			this.payerCountry = payerCountry;
		}

	
		public void setPayerEmail(String payerEmail) {
			this.payerEmail = payerEmail;
		}

		
		public void setPayerFullName(String payerFullName) {
			this.payerFullName = payerFullName;
		}

		
		public void setPayerid(String payerid) {
			this.payerid = payerid;
		}

	
		public void setPayeeEmail(String payeeEmail) {
			this.payeeEmail = payeeEmail;
		}

		
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
	
	

		

			
	
}
