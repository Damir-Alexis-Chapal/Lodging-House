package com.app_lodging_house.lodging_house.persistenceLayer.mapper;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public class AccommodationMapper {
    //To converto from entity to DTO
    public AccommodationDTO toDTO(AccommodationEntity e) {
        if (e == null) return null;
        AccommodationDTO dto = new AccommodationDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setPrice(e.getPrice() == null ? 0.0 : e.getPrice());
        dto.setMaxCapacity(e.getMaxCapacity());
        dto.setAvailable(e.isAvailable());
        dto.setOwnerId(e.getUser() != null ? e.getUser().getId() : null);
        return dto;
    }
    //To convert from CreateDTO to entity
    public AccommodationEntity toEntity(AccommodationCreateDTO dto) {
        if (dto == null) return null;
        AccommodationEntity e = new AccommodationEntity();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setMaxCapacity(dto.getMaxCapacity());
        e.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        if (dto.getOwnerId() != null) {
            UserEntity u = new UserEntity();
            u.setId(dto.getOwnerId());
            e.setUser(u);
        } else {
            e.setUser(null);
        }
        return e;
    }
    //To update an entity from DTO
    public void updateEntityFromDto(AccommodationCreateDTO dto, AccommodationEntity entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getMaxCapacity() != null) entity.setMaxCapacity(dto.getMaxCapacity());
        if (dto.getAvailable() != null) entity.setAvailable(dto.getAvailable());
        if (dto.getOwnerId() != null) {
            UserEntity u = new UserEntity();
            u.setId(dto.getOwnerId());
            entity.setUser(u);
        }
    }


}
