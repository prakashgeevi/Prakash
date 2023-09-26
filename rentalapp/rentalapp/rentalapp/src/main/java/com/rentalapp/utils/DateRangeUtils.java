package com.rentalapp.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DateRangeUtils {
	
	public static void main(String[] args) {
		//List<LocalDate> localDatesInRange = getLocalDatesInRange("2023-07-02", "2023-07-10");
		System.out.println(getDateList().size());
	}
	
	public static List<LocalDate> getLocalDatesInRange(String dateStringStart, String dateStringEnd) {
		  String pattern = "dd/MM/yyyy"; 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
       LocalDate startDate = LocalDate.parse(dateStringStart, formatter);
       LocalDate endDate = LocalDate.parse(dateStringEnd, formatter);
		List<LocalDate> localDateList = new ArrayList<>();
		LocalDate current = startDate;
		while (!current.isAfter(endDate)) {
			localDateList.add(current);
			current = current.plusDays(1);
		}
		return localDateList;

	}
 
	public static List<LocalDate> getLocalDatesInRange(LocalDate startDate, LocalDate endDate) {

		List<LocalDate> localDateList = new ArrayList<>();
		LocalDate current = startDate;

		while (!current.isAfter(endDate)) {
			localDateList.add(current);
			current = current.plusDays(1);
		}
		for (LocalDate localDate : localDateList) {
			System.out.println(localDate);
		}
		return localDateList;

	}

	public static List<LocalDate> getDateList() {
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusYears(3);
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
	    return IntStream.iterate(0, i -> i + 1)
	      .limit(numOfDaysBetween)
	      .mapToObj(i -> startDate.plusDays(i))
	      .collect(Collectors.toList()); 
		 
	}
	
	public static List<LocalDate> getDateListDateFormat(LocalDate startDate, LocalDate endDate){
		return Stream.iterate(startDate, date -> date.plusDays(1))
        .limit(startDate.until(endDate.plusDays(1)).getDays())
        .collect(Collectors.toList());
	}

}
