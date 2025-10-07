package com.app_lodging_house.lodging_house.persistenceLayer.mapper;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.BookingEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public class BookingMapper {

    //To DTO from entity
    public BookingDTO toDTO(BookingEntity entity) {
        if (entity == null) return null;

        BookingDTO dto = new BookingDTO();
        dto.setId(entity.getId());
        dto.setAccommodationId(
                entity.getAccommodation() != null ? entity.getAccommodation().getId() : null
        );
        dto.setOwnerId(
                entity.getUser() != null ? entity.getUser().getId() : null
        );
        dto.setDateCheckIn(entity.getDateCheckIn());
        dto.setDateCheckOut(entity.getDateCheckOut());
        dto.setGuestsNumber(entity.getGuestsNumber());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    //To Entity from createDTo
    public BookingEntity cDtoToEntity(BookingCreateDTO dto) {
        if (dto == null) return null;

        BookingEntity entity = new BookingEntity();

        AccommodationEntity accommodation = new AccommodationEntity();
        accommodation.setId(dto.getAccommodationId());
        entity.setAccommodation(accommodation);

        UserEntity user = new UserEntity();
        user.setId(dto.getOwnerId());
        entity.setUser(user);

        entity.setDateCheckIn(dto.getDateCheckIn());
        entity.setDateCheckOut(dto.getDateCheckOut());
        entity.setGuestsNumber(dto.getGuestsNumber());
        entity.setStatus("PENDING");

        return entity;
    }

    //To Entity From DTO
    public BookingEntity toEntity(BookingDTO dto) {
        if (dto == null) return null;

        BookingEntity entity = new BookingEntity();

        entity.setId(dto.getId());

        AccommodationEntity accommodation = new AccommodationEntity();
        accommodation.setId(dto.getAccommodationId());
        entity.setAccommodation(accommodation);

        UserEntity user = new UserEntity();
        user.setId(dto.getOwnerId());
        entity.setUser(user);

        entity.setDateCheckIn(dto.getDateCheckIn());
        entity.setDateCheckOut(dto.getDateCheckOut());
        entity.setGuestsNumber(dto.getGuestsNumber());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    public BookingEntity entityToCreateDTO(BookingCreateDTO dto) {
        BookingEntity entity = new BookingEntity();
        entity.setDateCheckIn(dto.getDateCheckIn());
        entity.setDateCheckOut(dto.getDateCheckOut());
        entity.setGuestsNumber(dto.getGuestsNumber());

        if (dto.getOwnerId() != null) {
            UserEntity user = new UserEntity();
            user.setId(dto.getOwnerId());
            entity.setUser(user);
        }
        if (dto.getAccommodationId() != null) {
            AccommodationEntity accommodation = new AccommodationEntity();
            accommodation.setId(dto.getAccommodationId());
            entity.setAccommodation(accommodation);
        }
        return entity;
    }
}
