package com.app_lodging_house.lodging_house.persistenceLayer.repository;

import com.app_lodging_house.lodging_house.persistenceLayer.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BookingRepository extends JpaRepository<BookingEntity,Long> {
    List<BookingEntity> findByUserId(Long id);
    List<BookingEntity> findByAccommodationId(Long id);

}
