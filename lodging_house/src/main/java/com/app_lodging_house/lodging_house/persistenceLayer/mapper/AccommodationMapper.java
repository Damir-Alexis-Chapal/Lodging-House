package com.app_lodging_house.lodging_house.persistenceLayer.mapper;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AccommodationMapper {

    //Entity To DTO
    @Mapping(target = "available", source = "isAvailable")
    @Mapping(target = "ownerId", source = "user.id")
    AccommodationDTO toDTO(AccommodationEntity entity);

    //CreateDTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "user", source = "ownerId")
    AccommodationEntity toEntity(AccommodationCreateDTO dto);

    //This is used to create a UserEntity from the ownerId
    default UserEntity map(Long ownerId) {
        if (ownerId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(ownerId);
        return user;
    }

}
