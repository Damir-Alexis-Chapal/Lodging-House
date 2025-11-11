package com.app_lodging_house.lodging_house.unit.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.impl.UserServiceImpl;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Unit Tests")
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO validUserDTO;
    private UserCreateDTO validUserCreateDTO;
    private UserCreateDTO validUserUpdateDTO;
    private UserDTO validUserUpdatedDTO;

    private Long validUserId;
    private LocalDate validDate;
    private List<UserDTO> userDTOS;
    private final PasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    private String securePassword;
    @BeforeEach
    void setUp() {
        validDate = LocalDate.parse("2025-10-10");
        validUserId = 1L;
        securePassword = passwordEncoder.encode("aksjhas");

        validUserDTO = new UserDTO(
                validUserId,
                "Alexis",
                "Alexis@gmail.com",
                securePassword,
                "3124887186",
                validDate,
                "https://personajes-de-ficcion-",
                "ADOMINATIOOOOON"
        );
        validUserCreateDTO = new UserCreateDTO(
                "Alexis",
                "Alexis@gmail.com",
                "aksjhas",
                "3124887186",
                validDate,
                "https://personajes-de-ficcion-",
                "ADOMINATIOOOOON"
        );

        validUserUpdateDTO = new UserCreateDTO(
                "Alexis chapal",
                "Alexis@gmail.com",
                "aksjhas",
                "3124887186",
                validDate,
                "https://personajes-de-ficcion-",
                "ADOMINATIOOOOON"
        );
        validUserUpdatedDTO = new UserDTO(
                validUserId,
                "Alexis chapal",
                "Alexis@gmail.com",
                securePassword,
                "3124887186",
                validDate,
                "https://personajes-de-ficcion-",
                "ADOMINATIOOOOON"
        );

        userDTOS = new ArrayList<>();
        userDTOS.add(validUserDTO);
    }

//==================================== To test createUser method=====================================

    @Test
    @DisplayName("CREATE - Should return a created User")
    void createUser_ValidData_ShouldReturnCreatedUser() {

        when(userDAO.save(validUserCreateDTO)).thenReturn(validUserDTO);
        UserDTO result = userService.createUser(validUserCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validUserId);

        verify(userDAO, times(1)).save(validUserCreateDTO);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when User DTO is null")
    void createUser_NullDTO_ShouldThrowException() {
        assertThatThrownBy(() -> userService.createUser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User DTO cannot be null");

        verify(userDAO, never()).save(null);
    }

//==================================== To test getById method=====================================

    @Test
    @DisplayName("READ - Should return user by ID")
    void getById_ValidData_ShouldReturnUser() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        UserDTO result = userService.getById(validUserId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validUserId);

        verify(userDAO, times(1)).findById(validUserId);
    }

    @Test
    @DisplayName("READ - Should throw exception when User ID is null")
    void getById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> userService.getById(null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID cannot be null");

        verify(userDAO, never()).findById(null);
    }

    @Test
    @DisplayName("READ - Should throw exception when user ID is less that 1 or negative")
    void getById_InvalidId_ShouldThrowException() {
        assertThatThrownBy(() -> userService.getById(0L)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID must be greater than 0");
        verify(userDAO, never()).findById(0L);

        assertThatThrownBy(() -> userService.getById(-10L)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID must be greater than 0");
        verify(userDAO, never()).findById(-10L);
    }

    @Test
    @DisplayName("READ - Should throw exception when a null DTO is returned by UserDAO")
    void getById_NoUserFound_ShouldThrowException() {

        when(userDAO.findById(validUserId)).thenReturn(null);
        assertThatThrownBy(() -> userService.getById(validUserId)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User not be found");

        verify(userDAO, times(1)).findById(validUserId);
    }

//==================================== To test getAll method=====================================

    @Test
    @DisplayName("READ - Should return a list with all Users")
    void getAll_ValidData_ShouldReturnUsers() {

        when(userDAO.findAll()).thenReturn(userDTOS);
        List<UserDTO> result = userService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.equals(userDTOS));

        verify(userDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ - Should throw exception when a null list is returned by UserDAO")
    void getAll_NullList_ShouldThrowException() {

        when(userDAO.findAll()).thenReturn(null);
        assertThatThrownBy(() -> userService.getAll()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User list could not be retrieved");

        verify(userDAO, times(1)).findAll();
    }

//==================================== To test updateUser method=====================================

    @Test
    @DisplayName("UPDATE - Should return updated user")
    void updateUser_ValidData_ShouldReturnUpdatedUser() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        when(userDAO.update(validUserId, validUserUpdateDTO)).thenReturn(validUserUpdatedDTO);
        UserDTO result = userService.updateUser(validUserId, validUserUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validUserId);

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, times(1)).update(validUserId, validUserUpdateDTO);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when User ID is null")
    void updateUser_NullId_ShouldThrowException() {

        assertThatThrownBy(() -> userService.updateUser(null, validUserUpdateDTO)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID cannot be null");

        verify(userDAO, never()).update(null, validUserUpdateDTO);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when User DTO is null")
    void updateUser_NullDTO_ShouldThrowException() {

        assertThatThrownBy(() -> userService.updateUser(validUserId, null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User DTO cannot be null");

        verify(userDAO, never()).update(validUserId, null);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when a null User DTO is returned by DAO")
    void updateUser_NullDtoReturned_ShouldReturnUpdatedUser() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        when(userDAO.update(validUserId, validUserUpdateDTO)).thenReturn(null);
        assertThatThrownBy(() ->userService.updateUser(validUserId, validUserUpdateDTO)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User cannot be updated");

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, times(1)).update(validUserId, validUserUpdateDTO);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when user cannot be found")
    void updateUser_NoUserFound_ShouldThrowException() {

        when(userDAO.findById(validUserId)).thenReturn(null);
        assertThatThrownBy(() -> userService.updateUser(validUserId, validUserUpdateDTO)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User not be found");

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, never()).deleteUser(validUserId);
    }

//==================================== To test deleteUser method=====================================

    @Test
    @DisplayName("DELETE - Should delete an existing user by ID")
    void deleteUser_ValidId_ShouldDeleteUser() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        userService.deleteUser(validUserDTO.getId());

        verify(userDAO, times(1)).findById(validUserDTO.getId());
        verify(userDAO, times(1)).deleteUser(validUserDTO.getId());
    }

    @Test
    @DisplayName("DELETE - Should throw exception when User ID is null")
    void deleteUser_NullId_ShouldThrowException() {

        assertThatThrownBy(() -> userService.deleteUser(null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID cannot be null");

        verify(userDAO, never()).deleteUser(null);
    }

    @Test
    @DisplayName("DELETE - Should throw exception when user ID is less that 1 or negative")
    void deleteUser_InvalidId_ShouldThrowException() {
        assertThatThrownBy(() -> userService.deleteUser(0L)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID must be greater than 0");
        verify(userDAO, never()).findById(0L);
        verify(userDAO, never()).deleteUser(0L);

        assertThatThrownBy(() -> userService.deleteUser(-10L)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID must be greater than 0");
        verify(userDAO, never()).findById(-10L);
        verify(userDAO, never()).deleteUser(-10L);

    }

    @Test
    @DisplayName("DELETE - Should throw exception when user cannot be found")
    void deleteUser_NoUserFound_ShouldThrowException() {

        when(userDAO.findById(validUserId)).thenReturn(null);
        assertThatThrownBy(() -> userService.deleteUser(validUserId)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User not be found");

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, never()).deleteUser(validUserId);
    }

    //==================================== To test updateUserRole method=====================================

    @Test
    @DisplayName("UPDATE - Should return user DTO")
    void updateUserRole_ValidData_ShouldReturnUserDTO() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        when(userDAO.updateRole(validUserId, validUserUpdateDTO)).thenReturn(validUserUpdatedDTO);
        UserDTO result = userService.updateUserRole(validUserId, validUserUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validUserId);

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, times(1)).updateRole(validUserId, validUserUpdateDTO);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when User ID is null")
    void updateUserRole_NullId_ShouldThrowException() {

        assertThatThrownBy(() -> userService.updateUserRole(null, validUserUpdateDTO)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User ID cannot be null");

        verify(userDAO, never()).updateRole(null, validUserUpdateDTO);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when User DTO is null")
    void updateUserRole_NullDTO_ShouldThrowException() {

        assertThatThrownBy(() -> userService.updateUserRole(validUserId, null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User DTO cannot be null");

        verify(userDAO, never()).updateRole(validUserId, null);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when a null user is returned by DAO updateRole")
    void updateUserRole_NullUser_ShouldThrowException() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        when(userDAO.updateRole(validUserId, validUserUpdateDTO)).thenReturn(null);
        assertThatThrownBy(() -> userService.updateUserRole(validUserId, validUserUpdateDTO)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User role cannot be updated");

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, times(1)).updateRole(validUserId, validUserUpdateDTO);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when user cannot be found")
    void updateUserRole_NoUserFound_ShouldThrowException() {

        when(userDAO.findById(validUserId)).thenReturn(null);
        assertThatThrownBy(() -> userService.updateUserRole(validUserId, validUserUpdateDTO)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("User not be found");

        verify(userDAO, times(1)).findById(validUserId);
        verify(userDAO, never()).updateRole(validUserId,  validUserUpdateDTO);
    }
}
