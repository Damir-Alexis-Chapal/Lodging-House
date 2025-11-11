package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;

public interface AuthService {
    String login(String email, String password);
    UserDTO getCurrentUser(String token);
}
