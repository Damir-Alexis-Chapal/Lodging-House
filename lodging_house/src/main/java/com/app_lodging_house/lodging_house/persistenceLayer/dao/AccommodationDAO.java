package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.AccommodationMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccommodationDAO {

    private final AccommodationMapper accommodationMapper;
    private final AccommodationRepository accommodationRepository;

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


}
