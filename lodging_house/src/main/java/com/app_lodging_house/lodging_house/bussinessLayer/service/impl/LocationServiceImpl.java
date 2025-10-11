package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.LocationService;
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
    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {
        LocationDTO location = locationDAO.save(locationDTO);
        return location;
    }
}
