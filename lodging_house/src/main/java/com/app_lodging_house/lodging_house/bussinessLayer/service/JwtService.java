package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;

public interface JwtService {
    String generateToken(UserEntity user);
    String extractEmail(String token);
    boolean isTokenValid(String token);
    String refreshToken(String refreshToken);

}
