package com.app_lodging_house.lodging_house.persistenceLayer.repository;

import com.app_lodging_house.lodging_house.persistenceLayer.entity.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<ServicesEntity, Long> {
}
