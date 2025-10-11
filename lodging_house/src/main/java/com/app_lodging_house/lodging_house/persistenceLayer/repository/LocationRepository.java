package com.app_lodging_house.lodging_house.persistenceLayer.repository;

import com.app_lodging_house.lodging_house.persistenceLayer.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity,Long> {
}
