package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.LocationService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.LocationDAO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationDAO locationDAO;
    private final AccommodationDAO accommodationDAO;
    @Override
    public LocationDTO addLocation(LocationCreateDTO locationDTO) {
        if(locationDTO == null){
            throw new IllegalArgumentException("LocationDTO cannot be null");
        }
        AccommodationDTO accommodation = accommodationDAO.findById(locationDTO.getAccommodationId());
        if(accommodation == null){
            throw new IllegalArgumentException("Accommodation not found");
        }
        LocationDTO location = locationDAO.save(locationDTO);
        return location;
    }
}
