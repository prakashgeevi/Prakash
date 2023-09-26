package com.wdm.bookingSystem.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wdm.bookingSystem.entity.Address;
import com.wdm.bookingSystem.entity.Theatre;
import com.wdm.bookingSystem.exceptionhandler.IdNotFoundException;
import com.wdm.bookingSystem.exceptionhandler.TheatreNotFoundException;
import com.wdm.bookingSystem.model.RequestTheatre;
import com.wdm.bookingSystem.repository.TheatreRepository;
import com.wdm.bookingSystem.service.ITheatreService;

@Service
public class TheatreServiceimpl implements ITheatreService {

	@Autowired
	TheatreRepository theatreRepository;

	public Theatre getTheatreId(Long theatreId) {
		return theatreRepository.findById(theatreId).orElseThrow(() -> new IdNotFoundException("theatreId not found"));
	}

	public Theatre saveTheatre(RequestTheatre requestTheatre) {
		
		 String theatrename = requestTheatre.getTheatrename();
		 
		 if (theatreRepository.existsByTheatrename(theatrename)) {
	            throw new IllegalArgumentException("Theatre with the same name already exists.");
	        }else {
		 			Address address = new Address();
		
					address.setCity(requestTheatre.getCity());
					address.setState(requestTheatre.getState());
					address.setStreet(requestTheatre.getStreet());
					address.setPhoneNumber(requestTheatre.getPhoneNumber());
					address.setPincode(requestTheatre.getPincode());
		
					Theatre theatre = new Theatre();			
					theatre.setTheatrename(requestTheatre.getTheatrename());
					theatre.setNumberofrows(requestTheatre.getNumberofrows());
					theatre.setNumberofseats(requestTheatre.getNumberofseats());
					theatre.setAddress(address);
					return theatreRepository.save(theatre);
	        }

	}

	public List<Theatre> getAllTheatre() {
		return theatreRepository.findAll();
	}

	public Theatre getTheatreById(long id) {
		Theatre findById = getTheatreId(id);
		if (findById != null) {
			return findById;
		} else {
			throw new TheatreNotFoundException("Id not present");
		}
	}

	public Theatre updateTheatre(long id, RequestTheatre requestTheatre) {

		Theatre obj = null;
		obj = getTheatreId(id);
		if (obj != null) {
			Address address = obj.getAddress();
			address.setCity(requestTheatre.getCity());
			address.setState(requestTheatre.getState());
			address.setStreet(requestTheatre.getStreet());
			address.setPhoneNumber(requestTheatre.getPhoneNumber());
			obj.setTheatrename(requestTheatre.getTheatrename());
			obj.setNumberofrows(requestTheatre.getNumberofrows());
			obj.setNumberofseats(requestTheatre.getNumberofseats());
			obj.setAddress(address);
			return theatreRepository.save(obj);
		} else {
			throw new TheatreNotFoundException("Id not present");
		}
	}

	public void deleteTheatre(long id) {

		Theatre findById = getTheatreId(id);

		if (findById != null) {
			theatreRepository.deleteById(id);
		} else {
			throw new TheatreNotFoundException("Id not present");
		}
	}

	public List<Theatre> filterbyTheatreName(String tName) {

		return theatreRepository.findBytheatrenameContaining(tName);

	}

}
