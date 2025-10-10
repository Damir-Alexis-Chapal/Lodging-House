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
    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO createBooking(BookingCreateDTO dto){
        BookingDTO bookingDTO = bookingDAO.save(dto);
        return bookingDTO;
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        BookingDTO bookingDTO = bookingDAO.findById(id);
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getByUserId(Long id){
        List<BookingDTO> bookingDTO = bookingDAO.findByUserId(id);
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getByAccommodationId(Long id){
        List<BookingDTO> bookingDTO = bookingDAO.findByAccommodationId(id);
        return bookingDTO;
    }

    @Override
    public BookingDTO cancelBooking(Long id) {
        BookingDTO cancelled = bookingDAO.saveCancelledBooking(id);
        return cancelled;

    }
}
