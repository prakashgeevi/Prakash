package com.wdm.bookingSystem.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdm.bookingSystem.entity.Actors;
import com.wdm.bookingSystem.entity.Cinema;
import com.wdm.bookingSystem.model.RequestCinema;
import com.wdm.bookingSystem.service.ICinemaService;
import com.wdm.bookingSystem.service.ITheatreService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CinemaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CinemaControllerTest {

	@Test
	void test() {
		//fail("Not yet implemented");
	}

	
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ICinemaService ICinemaService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void validateSaveBooking() throws Exception {
		when(ICinemaService.savecinema(any(RequestCinema.class)))
		.thenReturn(getBookingObject());
		
		MvcResult mvcResult = mockMvc.perform(post("/cinema")
				.content(objectMapper.writeValueAsString(getBookingRequestObject()))
				.contentType("application/json"))
				.andReturn();
				
//		String actualResponseBody = mvcResult.getResponse().getContentAsString();
//		assertThat(actualResponseBody);
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertThat(actualResponseBody).isEqualTo(objectMapper.writeValueAsString(getBookingObject()));
	}
	
	private Cinema getBookingRequestObject() {
		RequestCinema booking = new RequestCinema();
		Cinema dfdf = new Cinema();
		
		booking.setActor("sdfsf");
		booking.setActress("gdfgdsf");
		booking.setDescription("dfsdfsdf");
		booking.setDirecter("dffdfafafafaf");
		booking.setMovieName("dfsdfdfdf");
		booking.setMusicDirecter("dgsdgsdff");
		booking.setPoster("fgdfsdffad");
		booking.setUserid(1);
		return dfdf;
	}
	
	
	
	private Cinema getBookingObject() {
		Cinema booking = new Cinema();
		
		booking.setDescription("sgsdfsdfdf");
		booking.setId(2);
		booking.setLanguage("sdgsgsgsdgs");
		booking.setMovieName("dfdfdfdfa");
		booking.setPoster("dfdfadsfdf");
		booking.setRating(null);
		booking.setReleasedYear(null);
		return booking;
	}
	
	
	
	
}
