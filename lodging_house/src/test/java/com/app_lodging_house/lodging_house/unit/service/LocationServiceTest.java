package com.app_lodging_house.lodging_house.unit.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.LocationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.impl.LocationServiceImpl;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.LocationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LocationService - Unit Tests")
public class LocationServiceTest {

    @Mock
    LocationDAO locationDAO;

    @Mock
    AccommodationDAO accommodationDAO;

    @InjectMocks
    LocationServiceImpl locationService;

    private LocationDTO validLocationDTO;
    private LocationCreateDTO validLocationCreateDTO;
    private AccommodationDTO validAccommodationDTO;
    private double validPrice;
    private Long validAccommodationId;

    @BeforeEach
    void setUp() {
        validPrice = 100000;
        validAccommodationId = 1L;
        validAccommodationDTO = new AccommodationDTO(
                1L,
                "Hoja",
                "Best place ever",
                validPrice,
                5,
                true,
                1L
        );
        validLocationDTO = new LocationDTO(
                1L,
                "Bogota",
                "Cundinamarca",
                4.609710,
                -74.081750,
                "Cra. 7 #32-16",
                validAccommodationId
        );

        validLocationCreateDTO = new LocationCreateDTO(
                "Bogota",
                "Cundinamarca",
                4.609710,
                -74.081750,
                "Cra. 7 #32-16",
                validAccommodationId
        );

    }

//==================================== To test addLocation method=====================================

    @Test
    @DisplayName("CREATE - Should return created Location")
    void addLocation_ValidData_ShouldReturnCreatedLocation() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(locationDAO.save(validLocationCreateDTO)).thenReturn(validLocationDTO);
        LocationDTO result = locationService.addLocation(validLocationCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(validLocationDTO);

        verify(locationDAO, times(1)).save(validLocationCreateDTO);
        verify(accommodationDAO, times(1)).findById(validAccommodationId);

    }

    @Test
    @DisplayName("CREATE - Should throw exception when LocationDTO is null")
    void addLocation_NullDTO_ShouldThrowException() {
        assertThatThrownBy(() -> locationService.addLocation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LocationDTO cannot be null");

        verify(accommodationDAO, never()).findById(any());
        verify(locationDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Should throw exception when Accommodation not found")
    void addLocation_AccommodationNotFound_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> locationService.addLocation(validLocationCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation not found");

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
        verify(locationDAO, never()).save(validLocationCreateDTO);
    }

}
