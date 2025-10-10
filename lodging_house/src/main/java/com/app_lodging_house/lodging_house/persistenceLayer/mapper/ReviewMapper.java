package com.app_lodging_house.lodging_house.persistenceLayer.mapper;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.ReviewEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(ReviewEntity entity) {
        if (entity == null) {
            return null;
        }
        ReviewDTO dto = new ReviewDTO();
        dto.setId(entity.getId());
        dto.setAccommodationId(entity.getAccommodation().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setRate(entity.getRate());
        dto.setComment(entity.getComment());
        return dto;
    }

    public ReviewEntity toEntity(ReviewDTO dto) {
        if (dto == null) {
            return null;
        }
        ReviewEntity entity = new ReviewEntity();
        entity.setId(dto.getId());
        entity.setRate(dto.getRate());
        entity.setComment(dto.getComment());

        if (dto.getAccommodationId() != null) {
            AccommodationEntity accommodation = new AccommodationEntity();
            accommodation.setId(dto.getAccommodationId());
            entity.setAccommodation(accommodation);
        }

        if (dto.getUserId() != null) {
            UserEntity user = new UserEntity();
            user.setId(dto.getUserId());
            entity.setUser(user);
        }

        return entity;
    }
}
