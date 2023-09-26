package com.wdm.bookingSystem.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.wdm.bookingSystem.entity.ShowDetails;
import com.wdm.bookingSystem.model.RequestShowDetails;
import com.wdm.bookingSystem.model.ResponseShow;
import com.wdm.bookingSystem.model.ShowDTO;
@Service
public interface IShowDetailsService {

	public ShowDetails saveShowDetails(RequestShowDetails ShowDetails);

	public List<ShowDetails> getAllShowDetails();

	public ShowDetails getShowDetailsById(long id);

	public ShowDetails updateShowDetails(long id, RequestShowDetails requestShowDetails, long userid);

	public void deleteShowDetails(long id);
	
	public List<ShowDetails> getBytheatre(long Id);

	public ResponseShow getBycinema(long id);

	public List<ShowDTO> getCinemaShowtimes(String date,long cinemaId);


}
