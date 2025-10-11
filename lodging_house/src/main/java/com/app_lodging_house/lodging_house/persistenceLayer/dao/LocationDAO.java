package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.LocationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.LocationMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationDAO {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    public LocationDTO save(LocationDTO dto){
        LocationEntity locationEntity = locationMapper.toEntity(dto);
        LocationEntity savedLocation = locationRepository.save(locationEntity);
        return locationMapper.toDTO(savedLocation);
    }
}
