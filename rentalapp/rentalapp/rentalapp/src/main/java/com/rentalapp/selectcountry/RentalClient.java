package com.rentalapp.selectcountry;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalapp.exception.RentalAppException;

@Component
public class RentalClient extends BaseClient<Object> {

	private final Logger logger = LoggerFactory.getLogger(RentalClient.class);


	@Value("${rental.app.base.url}")
	private String baseUrl;
	 

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}

//	public Object createStateBox(Country request) {
//		String url = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(domainName).build().toString();
//		String requestPayload = convertCountryToString(request);
//		logger.debug("Generate email for campaign, request payload ={}", requestPayload);
//		return postRequestForResponse(url, requestPayload, Object.class);
//	}
	
	private String convertCountryToString(Country request) {
		try {
			return objectMapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RentalAppException(e.getMessage());
		}
		 
	}
	
	 


}
