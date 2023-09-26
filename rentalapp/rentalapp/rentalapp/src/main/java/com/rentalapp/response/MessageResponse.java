package com.rentalapp.response;

public class MessageResponse {
  private String message;
  private Boolean present;

  public MessageResponse(String message) {
    this.message = message;
  }

  public MessageResponse(Boolean emailExists) {
	this.present = emailExists;
  }

public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

public Boolean getPresent() {
	return present;
}

public void setPresent(Boolean present) {
	this.present = present;
}
}
