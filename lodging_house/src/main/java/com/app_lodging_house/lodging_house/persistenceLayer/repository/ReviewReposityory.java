package com.app_lodging_house.lodging_house.persistenceLayer.repository;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewReposityory extends JpaRepository<ReviewEntity,Long> {
    List<ReviewEntity> findAllByAccommodationId(Long id);
    List<ReviewEntity> findAllByUserId(Long id);
}
