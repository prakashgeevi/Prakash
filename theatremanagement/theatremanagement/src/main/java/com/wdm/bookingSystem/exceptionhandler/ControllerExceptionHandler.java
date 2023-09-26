package com.wdm.bookingSystem.exceptionhandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> NotFoundException(NotFoundException ex, WebRequest webRequest) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);

	}

	public ResponseEntity<ErrorMessage> NotFoundException(Exception ex, WebRequest webRequest) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(TheatreNotFoundException.class)
	public ResponseEntity<ErrorMessage> TheatreNotFoundException(TheatreNotFoundException ex, WebRequest webRequest) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CinemaNotFoundException.class)
	public ResponseEntity<ErrorMessage> CinemaNotFoundException(CinemaNotFoundException ex, WebRequest webRequest) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> UserNotFoundException(UserNotFoundException ex, WebRequest webRequest) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotAllowedException.class)
	public ResponseEntity<ErrorMessage> UserNotAllowedException(UserNotFoundException ex, WebRequest webRequest) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> handleBindingErrors(MethodArgumentNotValidException ex) {
		List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
		ErrorMessage errorResponse = new ErrorMessage(errorList.toString(), HttpStatus.BAD_REQUEST.toString());
		return new ResponseEntity<ErrorMessage>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
