package com.app_lodging_house.lodging_house.persistenceLayer.mapper;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public class UserMapper {
    //To convert from entity to DTO
    public UserDTO toDTO(UserEntity user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setBirthDate(user.getBirthDate());
        dto.setPersonalDescription(user.getPersonalDescription());
        dto.setProfileImg(user.getProfileImg());

        return dto;
    }

    //To convert from CreateDTO to entity
    public UserEntity toEntity(UserCreateDTO dto) {
        if (dto == null) return null;
        UserEntity entity = new UserEntity();

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPersonalDescription(dto.getPersonalDescription());
        entity.setProfileImg(dto.getProfileImg());

        return entity;
    }

    //To update and entity from DTO
    public void updateEntityFromDto(UserCreateDTO dto, UserEntity entity) {
        if (dto == null || entity == null) return;

        if(dto.getName() != null) entity.setName(dto.getName());
        if(dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if(dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if(dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if(dto.getBirthDate() != null) entity.setBirthDate(dto.getBirthDate());
        if(dto.getPersonalDescription() != null) entity.setPersonalDescription(dto.getPersonalDescription());
        if(dto.getProfileImg() != null) entity.setProfileImg(dto.getProfileImg());

    }

    //From DTO to entity
    public UserEntity dtoToEntity(UserDTO dto){
        if (dto == null) return null;
        UserEntity entity = new UserEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPersonalDescription(dto.getPersonalDescription());
        entity.setProfileImg(dto.getProfileImg());

        return entity;
    }

    //From DTO to createDTO
    public UserCreateDTO dtoToCreateDTO(UserDTO dto){
        if (dto == null) return null;
        UserCreateDTO cDTO = new UserCreateDTO();

        cDTO.setName(dto.getName());
        cDTO.setEmail(dto.getEmail());
        cDTO.setPassword(dto.getPassword());
        cDTO.setPhoneNumber(dto.getPhoneNumber());
        cDTO.setBirthDate(dto.getBirthDate());
        cDTO.setPersonalDescription(dto.getPersonalDescription());
        cDTO.setProfileImg(dto.getProfileImg());

        return cDTO;
    }

    //From entity to CreateDTO
    public UserCreateDTO entityToCreateDTO(UserEntity entity){
        if (entity == null) return null;
        UserCreateDTO cDTO = new UserCreateDTO();

        cDTO.setName(entity.getName());
        cDTO.setEmail(entity.getEmail());
        cDTO.setPassword(entity.getPassword());
        cDTO.setPhoneNumber(entity.getPhoneNumber());
        cDTO.setBirthDate(entity.getBirthDate());
        cDTO.setPersonalDescription(entity.getPersonalDescription());
        cDTO.setProfileImg(entity.getProfileImg());

        return cDTO;

    }


}
