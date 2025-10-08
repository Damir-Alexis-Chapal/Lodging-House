package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.UserService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.UserDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.UserMapper;
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

    private final UserMapper userMapper;
    private final UserDAO userDAO;

    @Override
    public UserDTO createUser(UserCreateDTO cDto){
        UserDTO userDTO = userDAO.save(cDto);
        return userDTO;
    }

    @Override
    public UserDTO getById(Long id){
        UserDTO dto = userDAO.findById(id);
        return dto;
    }

    @Override
    public List<UserDTO> getAll(){
        List<UserDTO> userDTOS = userDAO.findAll();
        return userDTOS;
    }

    @Override
    public UserDTO updateUser(Long id, UserCreateDTO dto) {
        UserDTO updated = userDAO.update(id, dto);
        return updated;
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

}
