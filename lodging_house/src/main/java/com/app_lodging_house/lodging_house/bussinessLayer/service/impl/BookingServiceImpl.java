package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.BookingService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.BookingDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.BookingEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.BookingMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;

    @Override
    public BookingDTO createBooking(BookingCreateDTO dto){
        if(dto==null){
            throw new IllegalArgumentException("Booking DTO cannot be null");
        }
        BookingDTO bookingDTO = bookingDAO.save(dto);
        return bookingDTO;
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        if(id==null){
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        if(id <= 0){
            throw new IllegalArgumentException("Booking ID cannot be negative or zero");
        }
        BookingDTO bookingDTO = bookingDAO.findById(id);
        if(bookingDTO==null){
            throw new IllegalArgumentException("Booking not be found");
        }
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getByUserId(Long id){
        if(id==null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if(id <= 0){
            throw new IllegalArgumentException("User ID cannot be negative or zero");
        }
        List<BookingDTO> bookingDTO = bookingDAO.findByUserId(id);
        if(bookingDTO==null){
            throw new IllegalArgumentException("Booking not be found");
        }
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getByAccommodationId(Long id){
        if(id==null){
            throw new IllegalArgumentException("Accommodation ID cannot be null");
        }
        if(id <= 0){
            throw new IllegalArgumentException("Accommodation ID cannot be negative or zero");
        }
        List<BookingDTO> bookingDTO = bookingDAO.findByAccommodationId(id);
        if(bookingDTO==null){
            throw new IllegalArgumentException("Booking not be found");
        }
        return bookingDTO;
    }

    @Override
    public BookingDTO cancelBooking(Long id) {
        if(id==null){
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        if(id <= 0){
            throw new IllegalArgumentException("Booking ID cannot be negative or zero");
        }
        BookingDTO cancelled = bookingDAO.saveCancelledBooking(id);
        if(cancelled==null){
            throw new IllegalArgumentException("Booking not be found");
        }
        return cancelled;

    }
}
