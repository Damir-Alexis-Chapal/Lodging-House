package com.app_lodging_house.lodging_house.persistenceLayer.mapper;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public class LocationMapper {

    // To convert from Entity to DTO
    public LocationDTO toDTO(LocationEntity entity) {
        if (entity == null) return null;

        LocationDTO dto = new LocationDTO();
        dto.setId(entity.getId());
        dto.setCity(entity.getCity());
        dto.setDepartment(entity.getDepartment());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setAddress(entity.getAddress());

        if (entity.getAccommodation() != null) {
            dto.setAccommodationId(entity.getAccommodation().getId());
        }

        return dto;
    }

    public LocationEntity cDtoToEntity(LocationCreateDTO dto) {
        if (dto == null) return null;

        LocationEntity entity = new LocationEntity();
        entity.setCity(dto.getCity());
        entity.setDepartment(dto.getDepartment());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setAddress(dto.getAddress());

        AccommodationEntity accommodation = new AccommodationEntity();
        accommodation.setId(dto.getAccommodationId());
        entity.setAccommodation(accommodation);

        return entity;
    }
}

