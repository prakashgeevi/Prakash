package com.wdm.bookingSystem.exceptionhandler;

public class EmailAlreadyInUseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
