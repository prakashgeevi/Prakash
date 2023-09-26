package com.wdm.bookingSystem.exceptionhandler;

public class UserNotAllowedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UserNotAllowedException(String msg) {
			
			super(msg);
		}
}
