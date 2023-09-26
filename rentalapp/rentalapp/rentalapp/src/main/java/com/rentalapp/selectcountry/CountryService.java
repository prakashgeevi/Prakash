package com.rentalapp.selectcountry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
 
public class CountryService {

    private final static String API_BASE_URL = "https://countriesnow.space/api/v0.1/countries";
    
    @Autowired
    RestTemplate restTemplate;

    public CountryData getAllCountries() {
        return restTemplate.getForObject(API_BASE_URL, CountryData.class);
    }

	public Set<String> getStatesByCountry(String country) {
		StateRequestDto dto = new StateRequestDto();
		dto.setCountry(country);
		String api = API_BASE_URL+"/states/q?country="+country;
		
		ObjectMapper map = new ObjectMapper();
		HttpHeaders head = new HttpHeaders();
		head.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		head.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<String> ent = new HttpEntity<String>(head);
		
		
		ResponseEntity<String> state = restTemplate.exchange(api, HttpMethod.GET, ent, String.class);
		System.out.println(state);
		
		try {
			StateData readValue = map.readValue(state.getBody(), StateData.class);
			return readValue.getData().getStates().stream().map(e -> e.getName()).collect(Collectors.toSet());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return null;
    }

    public Set<String> getCitiesByState(String state, String country) {
    	City countries = restTemplate.getForObject(API_BASE_URL + "/state/cities/q?country="+country+"&state="+state, City.class);
        return countries.getData().stream().collect(Collectors.toSet());
    }

}
