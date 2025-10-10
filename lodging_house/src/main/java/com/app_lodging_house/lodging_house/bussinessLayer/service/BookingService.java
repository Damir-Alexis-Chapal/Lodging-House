package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    //To create a new Booking
    BookingDTO createBooking(BookingCreateDTO dto);
    //To get a Booking by id
    BookingDTO getBookingById(Long id);
    //To get all Bookings by user id
    List<BookingDTO> getByUserId(Long id);
    //To get all Bookings by Accommodation id
    List<BookingDTO> getByAccommodationId(Long id);
    //To cancel booking
    BookingDTO cancelBooking(Long id);
}
