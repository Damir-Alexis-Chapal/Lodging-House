package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationImagesDTO;

import java.util.List;

public interface AccommodationService {
    //To create a new Accommodation
    AccommodationDTO createAccommodation(AccommodationCreateDTO accommodationCreateDTO);
    //To get Accommodations by id
    AccommodationDTO getAccommodationById(Long id);
    //To get Accommodations by name
    AccommodationDTO getAccommodationByName(String name);
    //To update Accommodation
    AccommodationDTO updateAccommodation(Long id, AccommodationCreateDTO dto);
    //To get all Accommodations
    List<AccommodationDTO> getAllAccommodations();
    //To delete Accommodation by id
    void deleteAccommodation(Long id);
    //To add services
    AccommodationDTO assignServicesToAccommodation(Long id, List<Long> serviceIds);
    //To save the images for accommodation
    List<AccommodationImagesDTO> saveImagesForAccommodation(List<AccommodationImagesDTO> images);
    //To get all accommodation images
    List<AccommodationImagesDTO> getAllAccommodationImages(Long id);
}
