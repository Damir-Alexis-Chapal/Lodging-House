package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationDTO;

public interface LocationService {
    //To add the Accommodations location
    LocationDTO addLocation(LocationCreateDTO locationDTO);
}
