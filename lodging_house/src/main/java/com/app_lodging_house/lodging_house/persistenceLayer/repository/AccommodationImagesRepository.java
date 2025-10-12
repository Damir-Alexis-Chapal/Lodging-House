package com.app_lodging_house.lodging_house.persistenceLayer.repository;

import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationImagesRepository extends JpaRepository<AccommodationImageEntity,Long> {
    List<AccommodationImageEntity> findByAccommodationId(Long accommodationId);
}
