package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationImagesDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationImageEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.AccommodationImagesRepository;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccommodationImagesDAO {

    private final AccommodationImagesRepository accommodationImagesRepository;
    private final AccommodationRepository accommodationRepository;

    public List<AccommodationImagesDTO> save(List<AccommodationImagesDTO> images){

        List<AccommodationImagesDTO> savedImages = new ArrayList<>();

        for (AccommodationImagesDTO imageDTO : images) {
            if (imageDTO == null || imageDTO.getImageUrl() == null) {
                continue;
            }

            AccommodationImageEntity entity = new AccommodationImageEntity();
            entity.setImageUrl(imageDTO.getImageUrl());

            if (imageDTO.getAccommodationId() != null) {
                AccommodationEntity accommodation = accommodationRepository.findById(imageDTO.getAccommodationId())
                        .orElseThrow(() -> new IllegalArgumentException("Accommodation not found with ID: " + imageDTO.getAccommodationId()));
                entity.setAccommodation(accommodation);
            } else {
                throw new IllegalArgumentException("Accommodation ID is required for image");
            }

            AccommodationImageEntity savedEntity = accommodationImagesRepository.save(entity);
            AccommodationImagesDTO savedDTO = new AccommodationImagesDTO();
            savedDTO.setAccommodationId(savedEntity.getAccommodation().getId());
            savedDTO.setImageUrl(savedEntity.getImageUrl());
            savedDTO.setId(savedEntity.getId());
            savedImages.add(savedDTO);
        }
        return savedImages;

    }
    public List<AccommodationImagesDTO> findAllByAccommodationId(Long id){
        List<AccommodationImageEntity> savedImages = accommodationImagesRepository.findByAccommodationId(id);
        List<AccommodationImagesDTO> images = new ArrayList<>();
        for(AccommodationImageEntity savedImage : savedImages){
            AccommodationImagesDTO image = new AccommodationImagesDTO();
            image.setAccommodationId(savedImage.getAccommodation().getId());
            image.setImageUrl(savedImage.getImageUrl());
            image.setId(savedImage.getId());
            images.add(image);
        }
        return images;
    }

}
