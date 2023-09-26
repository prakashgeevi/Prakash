package com.rentalapp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentalapp.entity.Property;
import com.rentalapp.entity.PropertySlots;
import com.rentalapp.exception.NotFoundException;
import com.rentalapp.model.PropertySlotDateRange;
import com.rentalapp.model.RequestSlots;
import com.rentalapp.repository.IUserAccountRespository;
import com.rentalapp.repository.PropertyRepository;
import com.rentalapp.repository.PropertyReservationRepository;
import com.rentalapp.repository.PropertySlotRepository;
import com.rentalapp.utils.DateRangeUtils;

@Service
public class PropertySlotsService {

	@Autowired
	PropertySlotRepository propertySlotRepo;

	@Autowired
	PropertyRepository propertyRepo;
	
	@Autowired
	PropertyReservationRepository propertyReserveRepo;

	@Autowired
	IUserAccountRespository userRepo;

	public Property saveSlot(RequestSlots requestSLot) {
		Property property = propertyRepo.findById(requestSLot.getPropertyId())
					.orElseThrow(() -> new NotFoundException("Property id not found"));
		
		
		List<PropertySlots> slotList = property.getPropertySlot();
		
		
		List<LocalDate> fullAvailableSlots = new LinkedList<>();
		for (PropertySlotDateRange range : requestSLot.getAvailableDays()) {
			String pattern = "dd/MM/yyyy";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalDate startDate = LocalDate.parse(range.getStartDate(), formatter);
			LocalDate endDate = LocalDate.parse(range.getEndDate(), formatter);
			List<LocalDate> localDatesInRange = DateRangeUtils.getLocalDatesInRange(range.getStartDate(),
					range.getEndDate());
			fullAvailableSlots.addAll(localDatesInRange);
		}
		
		for(PropertySlots slot : slotList) {
			if(fullAvailableSlots.contains(slot.getDate()) && slot.getStatus().equalsIgnoreCase("NOTAVAILABLE")){
				slot.setStatus("AVAILABLE");
			}
		}
		

		property.setPropertySlot(slotList);
		return propertyRepo.save(property);
	}

	public PropertySlots updateSlot(RequestSlots requestSlot, Long slotId) {
		
		PropertySlots slot = propertySlotRepo.findById(slotId)
					.orElseThrow(() -> new NotFoundException("Slot id not found"));
		//slot.setStatus(requestSlot.getStatus());
		return propertySlotRepo.save(slot);
	}

	public List<PropertySlots> getAllSlots(Long userId) {
		//List<PropertySlots> slots = propertySlotRepo.findByUserId(userId);
		return null;
	}

	public void deleteBySlot(Long slotId, Long propertyId) {
		Property property = propertyRepo.findById(propertyId)
				.orElseThrow(() -> new NotFoundException("Property id not found"));
	
	
		List<PropertySlots> slotList = property.getPropertySlot();
		for(PropertySlots slot : slotList) {
			if(slot.getId()==slotId && slot.getStatus().equalsIgnoreCase("AVAILABLE")){
				slot.setStatus("NOTAVAILABLE");
			}
		}

		property.setPropertySlot(slotList);
		propertyRepo.save(property);
		
	}
}
