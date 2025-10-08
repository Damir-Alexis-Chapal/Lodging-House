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
    //To convert from entity to DTO
    public AccommodationDTO toDTO(AccommodationEntity e) {
        if (e == null) return null;
        AccommodationDTO dto = new AccommodationDTO();

        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setPrice(e.getPrice());
        dto.setMaxCapacity(e.getMaxCapacity());
        dto.setAvailable(e.isAvailable());
        dto.setOwnerId(e.getUser().getId());

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
    //From DTO to entity
    public AccommodationEntity dtoToEntity(AccommodationDTO dto) {
        if (dto == null) return null;
        AccommodationEntity e = new AccommodationEntity();

        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setMaxCapacity(dto.getMaxCapacity());
        e.setAvailable(dto.isAvailable());
        if (dto.getOwnerId() != null) {
            UserEntity u = new UserEntity();
            u.setId(dto.getOwnerId());
            e.setUser(u);
        } else {
            e.setUser(null);
        }

        return e;
    }
    //From DTO to createDTO
    public AccommodationCreateDTO dtoToCreateDTO(AccommodationDTO dto) {
        if (dto == null) return null;
        AccommodationCreateDTO dto2 = new AccommodationCreateDTO();

        dto2.setName(dto.getName());
        dto2.setDescription(dto.getDescription());
        dto2.setPrice(dto.getPrice());
        dto2.setMaxCapacity(dto.getMaxCapacity());
        dto2.setAvailable(dto.isAvailable());
        dto2.setOwnerId(dto.getOwnerId());

        return dto2;
    }
    //From entity to CreateDTO
    public AccommodationCreateDTO entityToCreateDTO(AccommodationEntity e) {
        if (e == null) return null;
        AccommodationCreateDTO dto = new AccommodationCreateDTO();

        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setPrice(e.getPrice());
        dto.setMaxCapacity(e.getMaxCapacity());
        dto.setAvailable(e.isAvailable());
        dto.setOwnerId(e.getUser().getId());

        return dto;
    }

}
