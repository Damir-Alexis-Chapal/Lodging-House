package com.app_lodging_house.lodging_house.persistenceLayer.repository;

import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long> {
    Optional<AccommodationEntity> findByName(String name);

    @Query("SELECT a FROM AccommodationEntity a WHERE (:minPrice IS NULL OR a.price >= :minPrice) AND (:maxPrice IS NULL OR a.price <= :maxPrice)")
    List<AccommodationEntity> findByPriceRange(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);

    @Query("SELECT a FROM AccommodationEntity a JOIN LocationEntity l ON l.accommodation = a WHERE LOWER(l.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<AccommodationEntity> findByCity(@Param("city") String city);

    @Query("""
    SELECT DISTINCT a FROM AccommodationEntity a
    JOIN a.services s
    WHERE LOWER(s.name) IN :services
    """)
    List<AccommodationEntity> findByServices(
            @Param("services") List<String> services);


}
