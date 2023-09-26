package com.wdm.bookingSystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestBooking {

	@NotNull(message = "theatreId is mandatory")
	private long theatreId;

	@NotNull(message = "cinemaId is mandatory")
	private long cinemaId;

	@NotNull(message = "showDetailsid is mandatory")
	private long showDetailsid;

	@NotNull(message = "userId is mandatory")
	private long userId;

	private List<Integer> seatNumber;

	@NotBlank(message = "orderId is mandatory")
	private String orderId;
	@NotBlank(message = "bookingTime is mandatory")
	private String bookingTime;
	@NotBlank(message = "amount is mandatory")
	private double amount;
	@NotBlank(message = "amountType is mandatory")
	private String amountType;
	@NotBlank(message = "status is mandatory")
	private String status;
	@NotBlank(message = "payerCountry is mandatory")
	private String payerCountry;
	@NotBlank(message = "payerEmail is mandatory")
	private String payerEmail;
	@NotBlank(message = "payerFullName is mandatory")
	private String payerFullName;

	@NotBlank(message = "payerid is mandatory")
	private String payerid;
	@NotBlank(message = "payeeEmail is mandatory")
	private String payeeEmail;
	@NotBlank(message = "merchantId is mandatory")
	private String merchantId;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @return the bookingTime
	 */
	public String getBookingTime() {
		return bookingTime;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the amountType
	 */
	public String getAmountType() {
		return amountType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the payerCountry
	 */
	public String getPayerCountry() {
		return payerCountry;
	}

	/**
	 * @return the payerEmail
	 */
	public String getPayerEmail() {
		return payerEmail;
	}

	/**
	 * @return the payerFullName
	 */
	public String getPayerFullName() {
		return payerFullName;
	}

	/**
	 * @return the payerid
	 */
	public String getPayerid() {
		return payerid;
	}

	/**
	 * @return the payeeEmail
	 */
	public String getPayeeEmail() {
		return payeeEmail;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param bookingTime the bookingTime to set
	 */
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @param amountType the amountType to set
	 */
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param payerCountry the payerCountry to set
	 */
	public void setPayerCountry(String payerCountry) {
		this.payerCountry = payerCountry;
	}

	/**
	 * @param payerEmail the payerEmail to set
	 */
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	/**
	 * @param payerFullName the payerFullName to set
	 */
	public void setPayerFullName(String payerFullName) {
		this.payerFullName = payerFullName;
	}

	/**
	 * @param payerid the payerid to set
	 */
	public void setPayerid(String payerid) {
		this.payerid = payerid;
	}

	/**
	 * @param payeeEmail the payeeEmail to set
	 */
	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public long getTheatreId() {
		return theatreId;
	}

	public long setTheatreId(long theatreId) {
		return this.theatreId = theatreId;
	}

	public long getCinemaId() {
		return cinemaId;
	}

	public long setCinemaId(long cinemaId) {
		return this.cinemaId = cinemaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getShowDetailsid() {
		return showDetailsid;
	}

	public long setShowDetailsid(long showDetailsid) {
		return this.showDetailsid = showDetailsid;
	}

	public List<Integer> getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(List<Integer> seatNumber) {
		this.seatNumber = seatNumber;
	}

}
