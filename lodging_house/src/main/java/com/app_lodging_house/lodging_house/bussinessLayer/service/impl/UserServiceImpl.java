package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.UserService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.UserDAO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public UserDTO createUser(UserCreateDTO cDto) {
        if(cDto == null){
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        UserDTO userDTO = userDAO.save(cDto);
        return userDTO;
    }

    @Override
    public UserDTO getById(Long id){
        if(id == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if(id <= 0){
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
        UserDTO dto = userDAO.findById(id);
        if(dto == null){
            throw new IllegalArgumentException("User not be found");
        }
        return dto;
    }

    @Override
    public List<UserDTO> getAll(){
        List<UserDTO> userDTOS = userDAO.findAll();
        if(userDTOS == null){
            throw new IllegalArgumentException("User list could not be retrieved");
        }
        return userDTOS;
    }

    @Override
    public UserDTO updateUser(Long id, UserCreateDTO dto) {
        if(id == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if(dto == null){
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        if(userDAO.findById(id) == null){
            throw new IllegalArgumentException("User not be found");
        }
        UserDTO updated = userDAO.update(id, dto);

        if(updated == null){
            throw new IllegalArgumentException("User cannot be updated");
        }
        return updated;
    }

    @Override
    public void deleteUser(Long id) {
        if(id == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if(id <= 0){
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
        if(userDAO.findById(id) == null){
            throw new IllegalArgumentException("User not be found");
        }
        userDAO.deleteUser(id);
    }

    @Override
    public UserDTO updateUserRole(Long id, UserCreateDTO dto) {
        if(id == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if(dto == null){
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        if(userDAO.findById(id) == null){
            throw new IllegalArgumentException("User not be found");
        }

        UserDTO userDTO = userDAO.updateRole(id, dto);

        if(userDTO == null){
            throw new IllegalArgumentException("User role cannot be updated");
        }
        return userDTO;
    }

}
