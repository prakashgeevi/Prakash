/*
 * package com.wdm.bookingSystem.controller;
 * 
 * import static org.junit.Assert.assertThat; import static
 * org.mockito.ArgumentMatchers.any; import static org.mockito.Mockito.when;
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 * 
 * import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
 * import org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.test.context.junit.jupiter.SpringExtension; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.test.web.servlet.MvcResult;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * com.wdm.bookingSystem.service.ITheatreService;
 * 
 * 
 * @ExtendWith(SpringExtension.class)
 * 
 * @WebMvcTest(controllers = BookingController.class)
 * 
 * @AutoConfigureMockMvc(addFilters = false) class TheatreControllerTest {
 * 
 * @Test void test() { //fail("Not yet implemented"); }
 * 
 * @Autowired private MockMvc mockMvc;
 * 
 * @MockBean ITheatreService ITheatreService;
 * 
 * @Autowired private ObjectMapper objectMapper;
 * 
 * @Test void validateSaveBooking() throws Exception {
 * when(ITheatreService.saveTheatre(any(BookingRequest.class))).thenReturn(
 * getBookingObject());
 * 
 * MvcResult mvcResult = mockMvc.perform(post("/api/booking")
 * .content(objectMapper.writeValueAsString(getBookingRequestObject())).
 * contentType("application/json")) .andReturn();
 * 
 * String actualResponseBody = mvcResult.getResponse().getContentAsString();
 * assertThat(actualResponseBody).isEqualTo(objectMapper.writeValueAsString(
 * getBookingObject())); }
 * 
 * 
 * private BookingRequest getBookingRequestObject() { BookingRequest booking =
 * new BookingRequest(); booking.setAmount(100); booking.setClientId(1);
 * booking.setRoomId(1); booking.setSeatId(1); booking.setShowId(1); return
 * booking; }
 * 
 * 
 * }
 */