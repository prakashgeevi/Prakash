package com.rentalapp.response;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private String roles;
  private String status;
  private String profilePic;
  
  
  

  public JwtResponse(String token, Long id, String fname,String lname, String userName, String email, String roles, String status, String profilePic) {
	super();
	this.token = token;
	this.type = type;
	this.status = status;
	this.id = id;
	this.firstName = fname;
	this.lastName = lname;
	this.email = email;
	this.roles = roles;
	this.userName = userName;
	this.profilePic = profilePic;
	
}

public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getRoles() {
	return roles;
}

public void setRoles(String roles) {
	this.roles = roles;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getProfilePic() {
	return profilePic;
}

public void setProfilePic(String profilePic) {
	this.profilePic = profilePic;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

 


//public String getProfilePic() {
//	try {
//	String data = null;
//	if(answerFileExt != null && !answerFileExt.equals("")) {
//		if(answerFileExt.equals("pdf")) {
//			data = "data:application/pdf;base64,";
//		} else if(answerFileExt.equals("png")) {
//			data = "data:image/png;base64,";
//		} else if(answerFileExt.equals("jpg")) {
//			data = "data:image/jpg;base64,";
//		} else if(answerFileExt.equals("jpeg")) {
//			data = "data:image/jpeg;base64,";
//		}
//	}
//	
//	if(answerFilePath != null && answerFilePath.getBinaryStream().readAllBytes() != null && !Base64.getEncoder().encodeToString(answerFilePath.getBinaryStream().readAllBytes()).equals("")) {
//		return data+Base64.getEncoder().encodeToString(answerFilePath.getBinaryStream().readAllBytes());
//	}
//	return "";
//	} catch (Exception e) {
//		return "";
//	}
//}




  
}
