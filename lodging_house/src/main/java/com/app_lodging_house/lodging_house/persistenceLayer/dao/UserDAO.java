package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.RoleEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.UserMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO save(UserCreateDTO cDto) {
        UserEntity entity = userMapper.toEntity(cDto);

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        entity.setRole(role);

        String password = cDto.getPassword();
        String encriptedPassword = passwordEncoder.encode(password);
        entity.setPassword(encriptedPassword);

        UserEntity savedUser = userRepository.save(entity);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO findById(Long id){
        Optional<UserEntity> entity = userRepository.findById(id);
        return userMapper.toDTO(entity.orElse(null));
    }

    public List<UserDTO> findAll(){
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userDTOS.add(userMapper.toDTO(userEntity));
        }
        return userDTOS;
    }
    public UserDTO update(Long id, UserCreateDTO cDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(cDto.getName());
        existingUser.setEmail(cDto.getEmail());
        existingUser.setPassword(cDto.getPassword());
        existingUser.setPhoneNumber(cDto.getPhoneNumber());
        existingUser.setBirthDate(cDto.getBirthDate());
        existingUser.setProfileImg(cDto.getProfileImg());
        existingUser.setPersonalDescription(cDto.getPersonalDescription());

        UserEntity updated = userRepository.save(existingUser);
        return userMapper.toDTO(updated);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateRole(Long id, UserCreateDTO cDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(cDto.getName());
        existingUser.setEmail(cDto.getEmail());
        existingUser.setPassword(cDto.getPassword());
        existingUser.setPhoneNumber(cDto.getPhoneNumber());
        existingUser.setBirthDate(cDto.getBirthDate());
        existingUser.setProfileImg(cDto.getProfileImg());
        existingUser.setPersonalDescription(cDto.getPersonalDescription());
        RoleEntity role = new RoleEntity();
        role.setId(2L);
        existingUser.setRole(role);
        UserEntity updated = userRepository.save(existingUser);
        return userMapper.toDTO(updated);
    }

}
