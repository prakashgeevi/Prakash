package com.rentalapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentalapp.constant.Slot;
import com.rentalapp.entity.Property;
import com.rentalapp.entity.PropertyReservation;
import com.rentalapp.entity.PropertySlots;
import com.rentalapp.entity.User;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.exception.PropertyException;
import com.rentalapp.exception.RentalAppException;
import com.rentalapp.model.RequestReservation;
import com.rentalapp.repository.IUserAccountRespository;
import com.rentalapp.repository.PropertyRepository;
import com.rentalapp.repository.PropertyReservationRepository;
import com.rentalapp.repository.PropertySlotRepository;
import com.rentalapp.utils.DateRangeUtils;

@Service
public class ReservationService {

	@Autowired
	PropertyReservationRepository reservationRepo;

	@Autowired
	PropertyRepository propertyRepo;

	@Autowired
	IUserAccountRespository userRepo;

	@Autowired
	PropertySlotRepository propertySlotRepo;

	public PropertyReservation reserveProperty(RequestReservation requestReservation) {

		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate startDate = LocalDate.parse(requestReservation.getCheckInDate(), formatter);
			LocalDate endDate = LocalDate.parse(requestReservation.getCheckOutDate(), formatter);

			List<LocalDate> datesInRange = DateRangeUtils.getLocalDatesInRange(requestReservation.getCheckInDate(),
					requestReservation.getCheckOutDate());

			List<PropertySlots> list = propertySlotRepo
					.findByAndPropertyIdAndStatusAndDateIn(requestReservation.getPropertyId(), "AVAILABLE", datesInRange);

			User user = userRepo.findById(requestReservation.getTenantId())
					.orElseThrow(() -> new NotFoundException("User id not found"));

			if (user.getRole().equalsIgnoreCase("host")) {
				throw new RentalAppException("you are not applicable to booking any property");
			}

			Property property = propertyRepo.findById(requestReservation.getPropertyId())
					.orElseThrow(() -> new NotFoundException("Property id not found"));
 
			PropertyReservation propertyReservation = new PropertyReservation();
		
			if (list.isEmpty()) {
				throw new PropertyException("Already the selected slots is booked");
			}
			
			for (PropertySlots propertySlots : list) {
				propertySlots.setStatus("BOOKED");
				propertySlots.setPropertyReservation(propertyReservation);
			}
			
			propertyReservation.setPropertySLot(list);
			
			long totalDays = ChronoUnit.DAYS.between(startDate.minusDays(1), endDate);

			double paidAmount = totalDays * property.getCostPerDay();

			propertyReservation.setCheckInDate(startDate);
			propertyReservation.setCheckOutDate(endDate);
			propertyReservation.setPaidAmount(paidAmount);
			propertyReservation.setNumberOfPeoplesStay(requestReservation.getNumberOfPersonStay());
			propertyReservation.setProperty(property);
			propertyReservation.setStatus(Slot.BOOKED.name());
			propertyReservation.setUser(user);
			propertyReservation.setReservedOn(LocalDateTime.now());

//			PaymentDetail paymetDetail = new PaymentDetail();
//			paymetDetail.setAmount(paidAmount);
//			paymetDetail.setPaymentType(requestReservation.getPaymentType());
//			paymetDetail.setTransactionId(requestReservation.getTransactionId());
//			paymetDetail.setTransactionTime(LocalDateTime.now());

//			propertyReservation.setPaymentDetail(paymetDetail);

			return reservationRepo.save(propertyReservation);
 
		} catch (Exception e) {
			e.printStackTrace();
			throw new PropertyException(e.getMessage());
		}

	}

	public List<PropertyReservation> getAllReservePropertyList(Long tenantId) {
		User user = userRepo.findById(tenantId).orElseThrow(() -> new NotFoundException("tenant id not found"));
		if (!user.getRole().equalsIgnoreCase("tenant")) {
			throw new NotFoundException("user role mismatch");
		}

		List<PropertyReservation> reserList = reservationRepo.findByUserId(tenantId);

		Optional<Integer> map = reserList.stream().map(e -> e.getPropertySLot().size()).findAny();
		 
		
		return reserList;
	}
}
