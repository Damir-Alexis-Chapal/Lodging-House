package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;

public interface AccommodationService {
    //To create a new Accommodation
    AccommodationDTO createAccommodation(AccommodationCreateDTO accommodationCreateDTO);
    //To get Accommodations by id
    AccommodationDTO getAccommodationById(Long id);
    //To get Accommodations by name
    AccommodationDTO getAccommodationByName(String name);

}
