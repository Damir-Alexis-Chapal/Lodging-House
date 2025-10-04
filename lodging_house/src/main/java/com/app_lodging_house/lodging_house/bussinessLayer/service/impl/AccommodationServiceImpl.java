package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.AccommodationService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationDAO accommodationDAO;

    @Override
    public AccommodationDTO createAccommodation(AccommodationCreateDTO dto) {
        AccommodationDTO accommodationCreated = accommodationDAO.save(dto);
        return accommodationCreated;
    }

    @Override
    public AccommodationDTO getAccommodationById(Long id) {
        AccommodationDTO dto = accommodationDAO.findById(id);
        return dto;
    }

    @Override
    public AccommodationDTO getAccommodationByName(String name) {
        AccommodationDTO dto = accommodationDAO.findByName(name);
        return dto;
    }
}

