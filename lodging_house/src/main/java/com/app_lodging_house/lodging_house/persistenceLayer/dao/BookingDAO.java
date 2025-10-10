package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.BookingEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.BookingMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.AccommodationRepository;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.BookingRepository;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingDAO {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final BookingMapper bookingMapper;

    public BookingDTO save(BookingCreateDTO dto) {
        BookingEntity entity = bookingMapper.cDtoToEntity(dto);
        BookingEntity saved = bookingRepository.save(entity);

        return bookingMapper.toDTO(saved);
    }

    public BookingDTO findById(long id) {
        Optional<BookingEntity> entity = bookingRepository.findById(id);
        return bookingMapper.toDTO(entity.orElse(null));
    }

    public List<BookingDTO> findByUserId(long id) {
        List<BookingEntity> entities = bookingRepository.findByUserId(id);
        return entities.stream()
                .map(bookingMapper::toDTO)
                .toList();
    }

    public List<BookingDTO> findByAccommodationId(long id) {
        List<BookingEntity> entities = bookingRepository.findByAccommodationId(id);
        return entities.stream()
                .map(bookingMapper::toDTO)
                .toList();
    }

    public BookingDTO saveCancelledBooking(Long id) {
        BookingEntity entity = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));;
        entity.setStatus("CANCELLED");
        BookingEntity saved = bookingRepository.save(entity);
        return bookingMapper.toDTO(saved);
    }

}
