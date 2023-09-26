package com.wdm.bookingSystem.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wdm.bookingSystem.entity.Booking;
import com.wdm.bookingSystem.entity.BookingSeats;
import com.wdm.bookingSystem.entity.Cinema;
import com.wdm.bookingSystem.entity.ShowDetails;
import com.wdm.bookingSystem.entity.Theatre;
import com.wdm.bookingSystem.entity.TransactionDetail;
import com.wdm.bookingSystem.entity.User;
import com.wdm.bookingSystem.exceptionhandler.IdNotFoundException;
import com.wdm.bookingSystem.model.BookingResponce;
import com.wdm.bookingSystem.model.RequestBooking;
import com.wdm.bookingSystem.repository.BookingRepository;
import com.wdm.bookingSystem.repository.CinemaRepository;
import com.wdm.bookingSystem.repository.ShowRepository;
import com.wdm.bookingSystem.repository.TheatreRepository;
import com.wdm.bookingSystem.repository.UserRepository;
import com.wdm.bookingSystem.service.IBookingService;

@Service
public class BookingServiceImpl implements IBookingService {

	@Autowired
	BookingRepository bookingrepository;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	CinemaServiceImpl cinemaServiceImpl;

	@Autowired
	TheatreServiceimpl theatreServiceimpl;

	@Autowired
	ShowDetailsServiceImpl showDetailsServiceImpl;

	@Override
	public Booking savebooking(RequestBooking booking) {

		User user = userServiceImpl.getUserById(booking.getUserId());

		Cinema cinema = cinemaServiceImpl.getCinemaId(booking.getCinemaId());

		Theatre theatre = theatreServiceimpl.getTheatreId(booking.getTheatreId());

		ShowDetails showDetails = showDetailsServiceImpl.getShowDetailsId(booking.getShowDetailsid());

		TransactionDetail transactionDetail = new TransactionDetail();
		transactionDetail.setAmount(booking.getAmount());
		transactionDetail.setAmountType(booking.getAmountType());
		transactionDetail.setBookingTime(booking.getBookingTime());
		transactionDetail.setOrderId(booking.getOrderId());
		transactionDetail.setPayeeEmail(booking.getPayeeEmail());
		transactionDetail.setPayerCountry(booking.getPayerCountry());
		transactionDetail.setPayerEmail(booking.getPayerEmail());
		transactionDetail.setPayerFullName(booking.getPayerFullName());
		transactionDetail.setPayerid(booking.getPayerid());
		transactionDetail.setStatus(booking.getStatus());		
		transactionDetail.setMerchantId(booking.getMerchantId());

		Booking booking1 = new Booking();
		booking1.setTransactionDetail(transactionDetail);
		booking1.setUser(user);
		booking1.setCinema(cinema);
		booking1.setShowdetails(showDetails);
		booking1.setTheatre(theatre);

		List<BookingSeats> list = new ArrayList<>();
		for (Integer seats : booking.getSeatNumber()) {
			BookingSeats book = new BookingSeats();
			book.setBooking(booking1);
			book.setSeatNumber(seats);
			list.add(book);
		}

		booking1.setSeats(list);

		return bookingrepository.save(booking1);

	}

	public List<BookingResponce> findBookingDetails(long id) {

		List<Booking> listBooking = bookingrepository.findByUser(id);

		List<BookingResponce> booked = new ArrayList<>();
		for (Booking b : listBooking) {
			BookingResponce book = new BookingResponce();
			book.setPoster(b.getCinema().getPoster());

			book.setMovieName(b.getCinema().getMovieName());
			book.setCity(b.getTheatre().getAddress().getCity());
			book.setState(b.getTheatre().getAddress().getState());
			book.setNumber(b.getTheatre().getAddress().getPhoneNumber());
			book.setSeatNo(b.getSeats());
			book.setShowDate(b.getShowdetails().getDate());
			book.setShowTime(b.getShowdetails().getShowTime());
			book.setTheatreName(b.getTheatre().getTheatrename());

			booked.add(book);

		}

		return booked;

	}

	public List<Integer> getSeatsByBookingAndShow(long showId) {

		List<Integer> seats = new ArrayList<>();

		ShowDetails showDetails = showDetailsServiceImpl.getShowDetailsId(showId);

		if (showDetails != null) {
			List<Booking> listBooking = bookingrepository.findByShowdetailsId(showId);

			for (Booking booking : listBooking) {
				List<Integer> list = booking.getSeats().stream().map(e -> e.getSeatNumber())
						.collect(Collectors.toList());
				seats.addAll(list);
			}
		}
		return seats;
	}

}
