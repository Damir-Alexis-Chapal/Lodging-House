package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.AccommodationService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.AccommodationMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.AccommodationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationDAO accommodationDAO;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;

    @Override
    public AccommodationDTO createAccommodation(AccommodationCreateDTO dto) {
        AccommodationDTO accommodationCreated = accommodationDAO.save(dto);
        return accommodationCreated;
    }

    @Override
    public AccommodationDTO getAccommodationById(Long id) {
        AccommodationDTO dto = accommodationDAO.findById(id);
        return dto;
    }

    @Override
    public AccommodationDTO getAccommodationByName(String name) {
        AccommodationDTO dto = accommodationDAO.findByName(name);
        return dto;
    }

    @Override
    public AccommodationDTO updateAccommodation(Long id, AccommodationCreateDTO dto) {
        AccommodationEntity existing = accommodationMapper.dtoToEntity(accommodationDAO.findById(id));
        accommodationMapper.updateEntityFromDto(dto, existing);
        AccommodationDTO updated = accommodationDAO.save(accommodationMapper.entityToCreateDTO(existing));
        return updated;
    }

    @Override
    public List<AccommodationDTO> getAllAccommodations() {
        List<AccommodationEntity> accommodationEntities = accommodationRepository.findAll();

        List<AccommodationDTO> accommodationDTOS = new ArrayList<>();
        for(AccommodationEntity accommodationEntity : accommodationEntities) {
            accommodationDTOS.add(accommodationMapper.toDTO(accommodationEntity));
        }
        return accommodationDTOS;

    }

    @Override
    public void deleteAccommodation(Long id) {
        accommodationDAO.deleteAccommodation(id);
    }

}

