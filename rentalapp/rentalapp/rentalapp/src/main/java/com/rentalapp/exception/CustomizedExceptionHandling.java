package com.rentalapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

	 

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleidNotFoundException(IdNotFoundException idNotFoundException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.NOT_FOUND.toString());

		errorResponse.setMessage(idNotFoundException.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RentalAppException.class)
	public ResponseEntity<ErrorResponse> handleRentalAppExceptiionn(RentalAppException rentalAppException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.BAD_REQUEST.toString());

		errorResponse.setMessage(rentalAppException.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(PropertyException.class)
	public ResponseEntity<ErrorResponse> handlePropertyException(PropertyException propertyException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.BAD_REQUEST.toString());

		errorResponse.setMessage(propertyException.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BookingException.class)
	public ResponseEntity<ErrorResponse> handleBookingException(BookingException bookingException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.BAD_REQUEST.toString());

		errorResponse.setMessage(bookingException.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	 
//	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
//	public ResponseEntity<Object> handleBindingErrors(MethodArgumentNotValidException ex) {
//		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
//				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
//		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorList,
//				System.currentTimeMillis());
//		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
//	}

}
