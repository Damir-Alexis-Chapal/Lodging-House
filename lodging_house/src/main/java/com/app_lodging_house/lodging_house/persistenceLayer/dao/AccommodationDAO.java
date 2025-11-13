package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.ServicesEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.AccommodationMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.AccommodationRepository;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccommodationDAO {

    private final AccommodationMapper accommodationMapper;
    private final AccommodationRepository accommodationRepository;
    private final ServicesRepository servicesRepository;

    public AccommodationDTO save(AccommodationCreateDTO dto) {
        AccommodationEntity entity = accommodationMapper.toEntity(dto);
        AccommodationEntity saved = accommodationRepository.save(entity);
        return accommodationMapper.toDTO(saved);
    }

    public AccommodationDTO findById(Long id) {
        Optional<AccommodationEntity> entity = accommodationRepository.findById(id);
        return accommodationMapper.toDTO(entity.orElse(null));
    }

    public AccommodationDTO findByName(String name) {
        Optional<AccommodationEntity> entity = accommodationRepository.findByName(name);
        return accommodationMapper.toDTO(entity.orElse(null));
    }

    public void deleteAccommodation(Long id) {
        accommodationRepository.deleteById(id);
    }

    public List<AccommodationDTO> getAllAccommodations() {
        List<AccommodationEntity> accommodationEntities = accommodationRepository.findAll();

        List<AccommodationDTO> accommodationDTOS = new ArrayList<>();
        for(AccommodationEntity accommodationEntity : accommodationEntities) {
            accommodationDTOS.add(accommodationMapper.toDTO(accommodationEntity));
        }
        return accommodationDTOS;
    }

    public AccommodationDTO setServicesToAccommodation(Long id, List<Long> serviceIds) {
        AccommodationEntity accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        Set<ServicesEntity> services = new HashSet<>(servicesRepository.findAllById(serviceIds));
        accommodation.setServices(services);
        AccommodationEntity saved = accommodationRepository.save(accommodation);

        return accommodationMapper.toDTO(saved);
    }

    public List<AccommodationDTO> filterAccommodations(Double minPrice, Double maxPrice, String city, List<String> services) {
        try {
            List<AccommodationEntity> result = new ArrayList<>();
            boolean firstFilter = true;
            if (minPrice != null || maxPrice != null) {
                result = accommodationRepository.findByPriceRange(minPrice, maxPrice);
                firstFilter = false;
            }
            if (city != null && !city.trim().isEmpty()) {
                List<AccommodationEntity> byCity = accommodationRepository.findByCity(city.toLowerCase());
                if (firstFilter) {
                    result = byCity;
                    firstFilter = false;
                } else {
                    result.retainAll(byCity);
                }
            }
            if (services != null && !services.isEmpty()) {
                List<String> lowerServices = services.stream()
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
                List<AccommodationEntity> byServices = accommodationRepository.findByServices(lowerServices);
                if (firstFilter) {
                    result = byServices;
                    firstFilter = false;
                } else {
                    result.retainAll(byServices);
                }
            }
            if (firstFilter) {
                result = accommodationRepository.findAll();
            }
            if (result.isEmpty()) {
                return Collections.emptyList();
            }
            return result.stream()
                    .map(accommodationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error filtering accommodations: " +
                    (e.getMessage() != null ? e.getMessage() : "Unknown error"), e);
        }
    }


}
