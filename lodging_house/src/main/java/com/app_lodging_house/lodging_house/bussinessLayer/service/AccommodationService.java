package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
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
}
