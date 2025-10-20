package com.app_lodging_house.lodging_house.unit.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationImagesDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.impl.AccommodationServiceImpl;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationImagesDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.AccommodationEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.UserEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.AccommodationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccommodationService - Unit Tests")
public class AccommodationServiceTest {

    @Mock
    private AccommodationDAO accommodationDAO;

    @Mock
    private AccommodationMapper accommodationMapper;

    @Mock
    private AccommodationImagesDAO accommodationImagesDAO;
    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    private AccommodationCreateDTO validCdto;
    private AccommodationCreateDTO validCupdatedDto;
    private AccommodationDTO validAccommodationDTO;
    private AccommodationDTO validUpdatedDTO;
    private AccommodationEntity entity;
    private Long validAccommodationId;
    private Long validOwnerId;
    private double validPrice;
    private double invalidPrice;
    private List<AccommodationDTO> accommodations;
    private List<Long> serviceIds;
    private List<AccommodationImagesDTO> images;

    @BeforeEach
    void setUp() {
        validOwnerId = 1L;
        validPrice = 100000;
        invalidPrice = 0;
        validAccommodationId = 1L;

        validCdto = new AccommodationCreateDTO(
                "Hoja",
                "Best place ever",
                validPrice,
                5,
                true,
                validOwnerId
        );

        validAccommodationDTO = new AccommodationDTO(
                1L,
                "Hoja",
                "Best place ever",
                validPrice,
                5,
                true,
                validOwnerId
        );

        validCupdatedDto = new AccommodationCreateDTO(
                "agua",
                "Best place ever",
                validPrice,
                5,
                true,
                validOwnerId
        );

        validUpdatedDTO = new AccommodationDTO(
                1L,
                "Agua",
                "Best place ever",
                validPrice,
                5,
                true,
                validOwnerId
        );

        accommodations = new ArrayList<>();
        accommodations.add(validAccommodationDTO);

        serviceIds = new ArrayList<>();
        serviceIds.add(1L);
        serviceIds.add(2L);
        serviceIds.add(3L);

        images = new ArrayList<>();
        AccommodationImagesDTO existing = new AccommodationImagesDTO(
                1L,
                "https://holaxd",
                1L
        );
        AccommodationImagesDTO existing2 = new AccommodationImagesDTO(
                2L,
                "https://holaxd",
                1L
        );
        images.add(existing);
        images.add(existing2);

        entity = new AccommodationEntity();

        entity.setId(validAccommodationDTO.getId());
        entity.setName("agua");
        entity.setDescription("Best place ever");
        entity.setPrice(validPrice);
        entity.setMaxCapacity(5);
        entity.setAvailable(true);
        UserEntity user = new UserEntity();
        user.setId(validOwnerId);
        entity.setUser(user);

    }
//==================================== To test CreateAccommodation method=====================================
    @Test
    @DisplayName("CREATE - Should return created AccommodationDTO")
    void createAccommodation_ValidData_ShouldReturnCreatedAccommodation() {

        when(accommodationDAO.save(validCdto)).thenReturn(validAccommodationDTO);

        AccommodationDTO result = accommodationService.createAccommodation(validCdto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validAccommodationDTO.getId());

        verify(accommodationDAO, times(1)).save(validCdto);
    }
    @Test
    @DisplayName("CREATE - Should throw exception when name is null")
    void createAccommodation_NameNull_ShouldThrowException() {
        validCdto.setName(null);

        assertThatThrownBy(() -> accommodationService.createAccommodation(validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");

        verify(accommodationDAO, never()).save(validCdto);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when price is zero or negative")
    void createAccommodation_InvalidPrice_ShouldThrowException() {
        validCdto.setPrice(invalidPrice);

        assertThatThrownBy(() -> accommodationService.createAccommodation(validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("price");

        verify(accommodationDAO, never()).save(validCdto);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when maxCapacity is zero or negative")
    void createAccommodation_InvalidMaxCapacity_ShouldThrowException() {
        validCdto.setMaxCapacity(0);

        assertThatThrownBy(() -> accommodationService.createAccommodation(validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("maximum capacity");

        verify(accommodationDAO, never()).save(validCdto);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when ownerId is null")
    void createAccommodation_OwnerIdNull_ShouldThrowException() {
        validCdto.setOwnerId(null);

        assertThatThrownBy(() -> accommodationService.createAccommodation(validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("owner");

        verify(accommodationDAO, never()).save(validCdto);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when Accommodation is not saved")
    void createAccommodation_NotSaved_ShouldThrowException() {
        when(accommodationDAO.save(validCdto)).thenReturn(null);
        assertThatThrownBy(() -> accommodationService.createAccommodation(validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not saved");

        verify(accommodationDAO, times(1)).save(validCdto);
    }
//==================================== To test getAccommodationById method=====================================
    @Test
    @DisplayName("READ - Should return an existing Accommodation")
    void getAccommodationById_ValidId_ShouldReturnAccommodation() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);

        AccommodationDTO result = accommodationService.getAccommodationById(validAccommodationId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validAccommodationId);

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
    }

    @Test
    @DisplayName("READ - Should throw exception when id is null")
    void getAccommodationById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.getAccommodationById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id cannot be null");

        verify(accommodationDAO, never()).findById(null);
    }

    @Test
    @DisplayName("READ - Should throw exception when id is zero or negative")
    void getAccommodationById_InvalidId_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.getAccommodationById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id must be greater than zero");

        verify(accommodationDAO, never()).findById(0L);

        assertThatThrownBy(() -> accommodationService.getAccommodationById(-5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id must be greater than zero");

        verify(accommodationDAO, never()).findById(-5L);
    }

    @Test
    @DisplayName("READ - Should throw exception when accommodation is not found")
    void getAccommodationById_NotFound_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> accommodationService.getAccommodationById(validAccommodationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not found");

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
    }

//==================================== To test getAccommodationByName method=====================================
    @Test
    @DisplayName("READ - Should return an existing Accommodation")
    void getAccommodationByName_ValidName_ShouldReturnAccommodation() {

        when(accommodationDAO.findByName(validAccommodationDTO.getName())).thenReturn(validAccommodationDTO);
        AccommodationDTO result = accommodationService.getAccommodationByName(validAccommodationDTO.getName());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validAccommodationId);

        verify(accommodationDAO, times(1)).findByName(validAccommodationDTO.getName());
    }

    @Test
    @DisplayName("READ - Should throw exception when name is null")
    void getAccommodationByName_NullName_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.getAccommodationByName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name cannot be null or blank");

        verify(accommodationDAO, never()).findByName(null);
    }

    @Test
    @DisplayName("READ - Should throw exception when name is blank")
    void getAccommodationByName_BlankName_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.getAccommodationByName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name cannot be null or blank");

        verify(accommodationDAO, never()).findByName("");
    }

    @Test
    @DisplayName("READ - Should throw exception when accommodation with given name is not found")
    void getAccommodationByName_NotFound_ShouldThrowException() {
        String nonExistentName = "UnknownPlace";
        when(accommodationDAO.findByName(nonExistentName)).thenReturn(null);

        assertThatThrownBy(() -> accommodationService.getAccommodationByName(nonExistentName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not found");

        verify(accommodationDAO, times(1)).findByName(nonExistentName);
    }

//==================================== To test updateAccommodation method=====================================
    @Test
    @DisplayName("UPDATE - Should return updated Accommodation")
    void updateAccommodation_ValidData_ShouldReturnUpdatedAccommodation() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        when(accommodationMapper.dtoToEntity(validAccommodationDTO)).thenReturn(entity);
        doNothing().when(accommodationMapper).updateEntityFromDto(validCupdatedDto, entity);
        when(accommodationMapper.entityToCreateDTO(entity)).thenReturn(validCupdatedDto);
        when(accommodationDAO.save(validCupdatedDto)).thenReturn(validUpdatedDTO);

        AccommodationDTO result = accommodationService.updateAccommodation(validAccommodationDTO.getId(), validCupdatedDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validAccommodationDTO.getId());

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationMapper, times(1)).entityToCreateDTO(entity);
        verify(accommodationMapper, times(1)).dtoToEntity(validAccommodationDTO);
        verify(accommodationMapper, times(1)).updateEntityFromDto(validCupdatedDto, entity);
        verify(accommodationDAO, times(1)).save(validCupdatedDto);

    }

    @Test
    @DisplayName("UPDATE - Should throw exception when id is null")
    void updateAccommodation_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.updateAccommodation(null, validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation id cannot be null");

        verify(accommodationDAO, never()).save(validCdto);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when DTO is null")
    void updateAccommodation_NullDTO_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.updateAccommodation(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation data cannot be null");
        verify(accommodationDAO, never()).save(validCdto);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when accommodation not found")
    void updateAccommodation_NotFound_ShouldThrowException() {

        when(accommodationDAO.findById(99L)).thenReturn(null);
        when(accommodationMapper.dtoToEntity(null)).thenReturn(null);

        assertThatThrownBy(() -> accommodationService.updateAccommodation(99L, validCdto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation not found");

        verify(accommodationDAO, times(1)).findById(99L);
    }

    @Test
    @DisplayName("UPDATE - Should throw exception when accommodation is not updated")
    void updateAccommodation_NotUpdated_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        when(accommodationMapper.dtoToEntity(validAccommodationDTO)).thenReturn(entity);
        doNothing().when(accommodationMapper).updateEntityFromDto(validCupdatedDto, entity);
        when(accommodationMapper.entityToCreateDTO(entity)).thenReturn(validCupdatedDto);
        when(accommodationDAO.save(validCupdatedDto)).thenReturn(null);

        assertThatThrownBy(() -> accommodationService.updateAccommodation(validAccommodationDTO.getId(), validCupdatedDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation not updated");

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationMapper, times(1)).entityToCreateDTO(entity);
        verify(accommodationMapper, times(1)).dtoToEntity(validAccommodationDTO);
        verify(accommodationMapper, times(1)).updateEntityFromDto(validCupdatedDto, entity);
        verify(accommodationDAO, times(1)).save(validCupdatedDto);
    }

//==================================== To test getAllAccommodations method=====================================

    @Test
    @DisplayName("READ - Should return a list with all Accommodations")
    void getAllAccommodations_ShouldReturnAllAccommodations() {

        when(accommodationDAO.getAllAccommodations()).thenReturn(accommodations);
        List<AccommodationDTO> result = accommodationService.getAllAccommodations();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(accommodations);

        verify(accommodationDAO, times(1)).getAllAccommodations();

    }

    @Test
    @DisplayName("READ - Should throw exception when accommodation not found")
    void getAllAccommodations_NotFound_ShouldThrowException() {

        when(accommodationDAO.getAllAccommodations()).thenReturn(null);

        assertThatThrownBy(() -> accommodationService.getAllAccommodations())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodations not found");

        verify(accommodationDAO, times(1)).getAllAccommodations();
    }

//==================================== To test deleteAccommodation method=====================================
    @Test
    @DisplayName("DELETE - Should delete an existing Accommodation by ID")
    void deleteAccommodation_ValidId_ShouldDeleteAccommodation() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        accommodationService.deleteAccommodation(validAccommodationDTO.getId());

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationDAO, times(1)).deleteAccommodation(validAccommodationDTO.getId());
    }

    @Test
    @DisplayName("DELETE - Should throw exception when accommodation not found")
    void deleteAccommodation_NotFound_ShouldThrowException() {
        when(accommodationDAO.findById(99L)).thenReturn(null);
        assertThatThrownBy(() -> accommodationService.deleteAccommodation(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation not found");

        verify(accommodationDAO, times(1)).findById(99L);
        verify(accommodationDAO, never()).deleteAccommodation(99L);
    }

//==================================== To test assignServicesToAccommodation method=====================================

    @Test
    @DisplayName("CREATE - Should return an existing Accommodation by id")
    void assignServicesToAccommodation_ValidData_ShouldCreateAccommodation() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        when(accommodationDAO.setServicesToAccommodation(validAccommodationId, serviceIds)).thenReturn(validAccommodationDTO);
        AccommodationDTO result = accommodationService.assignServicesToAccommodation(validAccommodationId, serviceIds);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(validAccommodationDTO);

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationDAO, times(1)).setServicesToAccommodation(validAccommodationId, serviceIds);

    }

    @Test
    @DisplayName("CREATE - Should throw exception when id is null")
    void  assignServicesToAccommodation_IdNull_ShouldThrowException() {

        assertThatThrownBy(() -> accommodationService.assignServicesToAccommodation(null, serviceIds))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation id cannot be null");

        verify(accommodationDAO, never()).setServicesToAccommodation(null, serviceIds);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when serviceIds list is null")
    void assignServicesToAccommodation_ServiceIdsNull_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.assignServicesToAccommodation(validAccommodationId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation service IDs cannot be null or blank");

        verify(accommodationDAO, never()).setServicesToAccommodation(null, null);
    }
    @Test
    @DisplayName("CREATE - Should throw exception when Accommodation cannot be found")
    void assignServicesToAccommodation_AccommodationNotFound_ShouldThrowException() {
        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(null);
        assertThatThrownBy(() -> accommodationService.assignServicesToAccommodation(validAccommodationDTO.getId(), serviceIds)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("Accommodation not found");

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationDAO, never()).setServicesToAccommodation(null, serviceIds);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when services cannot be assigned")
    void assignServicesToAccommodation_NotAssigned_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        when(accommodationDAO.setServicesToAccommodation(validAccommodationDTO.getId(), serviceIds)).thenReturn(null);
        assertThatThrownBy(() -> accommodationService.assignServicesToAccommodation(validAccommodationDTO.getId(), serviceIds)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("Accommodation services not saved");

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationDAO, never()).setServicesToAccommodation(null, serviceIds);
    }

//==================================== To test saveImagesForAccommodation method=====================================

    @Test
    @DisplayName("CREATE - Should return an AccommodationImagesDTOs list")
    void saveImagesForAccommodation_ValidData_ShouldReturnImages() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        when(accommodationImagesDAO.save(images)).thenReturn(images);
        List<AccommodationImagesDTO> result = accommodationService.saveImagesForAccommodation(images);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(images);

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationImagesDAO, times(1)).save(images);

    }

    @Test
    @DisplayName("CREATE - Should throw exception when images list is null")
    void saveImagesForAccommodation_NullList_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.saveImagesForAccommodation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation images cannot be null");

        verify(accommodationImagesDAO, never()).save(null);

    }

    @Test
    @DisplayName("CREATE - Should throw exception when images list is blank")
    void saveImagesForAccommodation_BlankList_ShouldThrowException() {
        List<AccommodationImagesDTO> images = new ArrayList<>();

        assertThatThrownBy(() -> accommodationService.saveImagesForAccommodation(images))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation images cannot be empty");

        verify(accommodationImagesDAO, never()).save(images);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when images list is not saved")
    void saveImagesForAccommodation_ImagesNotSaved_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationDTO.getId())).thenReturn(validAccommodationDTO);
        when(accommodationImagesDAO.save(images)).thenReturn(null);
        assertThatThrownBy(() -> accommodationService.saveImagesForAccommodation(images))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation images not saved");

        verify(accommodationDAO, times(1)).findById(validAccommodationDTO.getId());
        verify(accommodationImagesDAO, times(1)).save(images);
    }

//==================================== To test getAllAccommodationImages method=====================================

    @Test
    @DisplayName("READ -  Should return Accommodations images by ID")
    void getImagesById_ValidData_ShouldReturnImages() {


        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(accommodationImagesDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(images);

        List<AccommodationImagesDTO> result = accommodationService.getAllAccommodationImages(validAccommodationId);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(images);

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
        verify(accommodationImagesDAO, times(1)).findAllByAccommodationId(validAccommodationId);

    }

    @Test
    @DisplayName("READ - Should throw exception when id is null")
    void getAllAccommodationImages_IdNull_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.getAllAccommodationImages(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation id cannot be null");

        verify(accommodationDAO, never()).findById(any());
        verify(accommodationImagesDAO, never()).findAllByAccommodationId(any());
    }

    @Test
    @DisplayName("READ - Should throw exception when id <= 0")
    void getAllAccommodationImages_IdLessThanOrEqualZero_ShouldThrowException() {
        assertThatThrownBy(() -> accommodationService.getAllAccommodationImages(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation id must be greater than zero");

        verify(accommodationDAO, never()).findById(any());
        verify(accommodationImagesDAO, never()).findAllByAccommodationId(any());
    }

    @Test
    @DisplayName("READ - Should throw exception when accommodation not found")
    void getAllAccommodationImages_AccommodationNotFound_ShouldThrowException() {

        when(accommodationDAO.findById(1L)).thenReturn(null);
        assertThatThrownBy(() -> accommodationService.getAllAccommodationImages(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation not found");

        verify(accommodationDAO, times(1)).findById(1L);
        verify(accommodationImagesDAO, never()).findAllByAccommodationId(any());
    }

    @Test
    @DisplayName("READ - Should throw exception when accommodation images cannot be found")
    void getAllAccommodationImages_ImagesNotFound_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(accommodationImagesDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> accommodationService.getAllAccommodationImages(validAccommodationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation images not found");

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
        verify(accommodationImagesDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }
}
