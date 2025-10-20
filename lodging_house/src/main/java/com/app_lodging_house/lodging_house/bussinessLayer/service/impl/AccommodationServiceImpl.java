package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationImagesDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.AccommodationService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationImagesDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.AccommodationMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationDAO accommodationDAO;
    private final AccommodationMapper accommodationMapper;
    private final AccommodationImagesDAO accommodationImagesDAO;

    @Override
    public AccommodationDTO createAccommodation(AccommodationCreateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Accommodation data cannot be null");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Accommodation name cannot be null or blank");
        }
        if (dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Accommodation price must be greater than zero");
        }
        if(dto.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Accommodation maximum capacity must be greater than zero");
        }
        if (dto.getOwnerId() == null) {
            throw new IllegalArgumentException("Accommodation must have an owner ID");
        }
        AccommodationDTO accommodationCreated = accommodationDAO.save(dto);
        if(accommodationCreated==null){
            throw new IllegalArgumentException("Accommodation not saved");
        }
        return accommodationCreated;
    }

    @Override
    public AccommodationDTO getAccommodationById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Accommodation id cannot be null");
        }
        if(id <= 0) {
            throw new IllegalArgumentException("Accommodation id must be greater than zero");
        }
        AccommodationDTO dto = accommodationDAO.findById(id);

        if(dto == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        return dto;
    }

    @Override
    public AccommodationDTO getAccommodationByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Accommodation name cannot be null or blank");
        }
        AccommodationDTO dto = accommodationDAO.findByName(name);
        if(dto == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        return dto;
    }

    @Override
    public AccommodationDTO updateAccommodation(Long id, AccommodationCreateDTO dto) {
        if (id == null) {
            throw new IllegalArgumentException("Accommodation id cannot be null");
        }
        if (dto == null) {
            throw new IllegalArgumentException("Accommodation data cannot be null");
        }
        AccommodationEntity existing = accommodationMapper.dtoToEntity(accommodationDAO.findById(id));
        if (existing == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        accommodationMapper.updateEntityFromDto(dto, existing);
        AccommodationDTO updated = accommodationDAO.save(accommodationMapper.entityToCreateDTO(existing));
        if(updated==null){
            throw new IllegalArgumentException("Accommodation not updated");
        }
        return updated;
    }

    @Override
    public List<AccommodationDTO> getAllAccommodations() {
        List<AccommodationDTO> accommodationDTOS = accommodationDAO.getAllAccommodations();
        if(accommodationDTOS == null) {
            throw new IllegalArgumentException("Accommodations not found");
        }
        return accommodationDTOS;
    }

    @Override
    public void deleteAccommodation(Long id) {
        AccommodationDTO dto = accommodationDAO.findById(id);
        if(dto == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        accommodationDAO.deleteAccommodation(id);
    }

    @Override
    public AccommodationDTO assignServicesToAccommodation(Long id, List<Long> serviceIds) {
        if(id == null){
            throw new IllegalArgumentException("Accommodation id cannot be null");
        }
        if(serviceIds == null || serviceIds.isEmpty()) {
            throw new IllegalArgumentException("Accommodation service IDs cannot be null or blank");
        }
        if(accommodationDAO.findById(id) == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        AccommodationDTO accommodationDTO = accommodationDAO.setServicesToAccommodation(id, serviceIds);

        if(accommodationDTO == null) {
            throw new IllegalArgumentException("Accommodation services not saved");
        }
        return accommodationDTO;
    }

    @Override
    public List<AccommodationImagesDTO> saveImagesForAccommodation(List<AccommodationImagesDTO> images){
        if(images == null) {
            throw new IllegalArgumentException("Accommodation images cannot be null");
        }
        if(images.isEmpty()) {
            throw new IllegalArgumentException("Accommodation images cannot be empty");
        }
        if(accommodationDAO.findById(images.get(0).getId()) == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        List<AccommodationImagesDTO> imagesSaved = accommodationImagesDAO.save(images);
        if(imagesSaved == null) {
            throw new IllegalArgumentException("Accommodation images not saved");
        }
        return imagesSaved;
    }

    @Override
    public List<AccommodationImagesDTO> getAllAccommodationImages(Long id){
        if(id == null) {
            throw new IllegalArgumentException("Accommodation id cannot be null");
        }
        if(id <= 0) {
            throw new IllegalArgumentException("Accommodation id must be greater than zero");
        }
        AccommodationDTO dto = accommodationDAO.findById(id);

        if(dto == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }

        List<AccommodationImagesDTO> images = accommodationImagesDAO.findAllByAccommodationId(id);
        if(images == null) {
            throw new IllegalArgumentException("Accommodation images not found");
        }
        return images;
    }
}

