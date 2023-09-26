package com.wdm.bookingSystem.exceptionhandler;

public class CinemaNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	
	public CinemaNotFoundException(String msg) {

		super(msg);
	}
}
