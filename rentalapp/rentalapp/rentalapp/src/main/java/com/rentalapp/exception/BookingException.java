package com.rentalapp.exception;

public class BookingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public BookingException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

}
