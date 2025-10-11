package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;

import java.util.List;

public interface UserService {
    //To create/register a new user
    UserDTO createUser(UserCreateDTO dto);
    //To get user's by id
    UserDTO getById(Long id);
    //To get all users
    List<UserDTO> getAll();
    //To update users
    UserDTO updateUser(Long id, UserCreateDTO dto);
    //To delete users
    void deleteUser(Long id);
    //to update the role
    UserDTO updateUserRole(Long id, UserCreateDTO dto);
}
