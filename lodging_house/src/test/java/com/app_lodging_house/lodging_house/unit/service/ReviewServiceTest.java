package com.app_lodging_house.lodging_house.unit.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.impl.ReviewServiceImpl;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.AccommodationDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.ReviewDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReviewService - Unit Tests")
public class ReviewServiceTest {

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private UserDAO userDAO;

    @Mock
    private AccommodationDAO accommodationDAO;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewDTO validDTO;
    private ReviewCreateDTO validReviewCreateDTO;
    private AccommodationDTO validAccommodationDTO;
    private UserDTO validUserDTO;
    private int validRate;
    private int invalidRateMin;
    private int invalidRateMax;
    private double accumulator;
    private double validPrice;
    private Long validAccommodationId;
    private Long validUserId;
    private List<ReviewDTO> reviewDTOS;
    private LocalDate validDate;


    @BeforeEach
    void setUp(){
        validRate = 1;
        invalidRateMin = 0;
        invalidRateMax = 6;
        validAccommodationId = 1L;
        validUserId = 1L;
        validPrice = 100000;
        validDate = LocalDate.parse("2025-10-10");

        validDTO = new ReviewDTO(
                1L,
                1L,
                1L,
                validRate,
                "hola"
        );
        validReviewCreateDTO = new ReviewCreateDTO(
                1L,
                1L,
                validRate,
                "hola"
        );

        validAccommodationDTO = new AccommodationDTO(
                1L,
                "Hoja",
                "Best place ever",
                validPrice,
                5,
                true,
                1L
        );

        validUserDTO = new UserDTO(
                validUserId,
                "Alexis",
                "Alexis@gmail.com",
                "aksjhas",
                "3124887186",
                validDate,
                "https://personajes-de-ficcion-",
                "ADOMINATIOOOOON"
        );

        reviewDTOS = new ArrayList<>();
        reviewDTOS.add(validDTO);

        int accumulator2 = 0;
        for (ReviewDTO dto : reviewDTOS) {
            accumulator2 += dto.getRate();
        }

        accumulator = (double )accumulator2 / reviewDTOS.size();
    }

//==================================== To test createReview method=====================================

    @Test
    @DisplayName("CREATE - Should return created review")
    void createReview_ValidData_ShouldReturnCreatedReview() {

        when(reviewDAO.save(validReviewCreateDTO)).thenReturn(validDTO);
        ReviewDTO result = reviewService.createReview(validReviewCreateDTO);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(validDTO);

        verify(reviewDAO, times(1)).save(validReviewCreateDTO);

    }

    @Test
    @DisplayName("CREATE - Should throw exception when Review DTO is null")
    void createReview_NullDTO_ShouldThrowException() {
        assertThatThrownBy(() -> reviewService.createReview(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Review DTO cannot be null");

        verify(reviewDAO, never()).save(null);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when rate is greater than 5")
    void createReview_RateGreaterThanFive_ShouldThrowException() {
        validReviewCreateDTO.setRate(invalidRateMax);
        assertThatThrownBy(() -> reviewService.createReview(validReviewCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The rate must be between 1 and 5");

        verify(reviewDAO, never()).save(validReviewCreateDTO);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when rate is less than 1")
    void createReview_RateLessThanOne_ShouldThrowException() {
        validReviewCreateDTO.setRate(invalidRateMin);
        assertThatThrownBy(() -> reviewService.createReview(validReviewCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The rate must be between 1 and 5");

        verify(reviewDAO, never()).save(validReviewCreateDTO);
    }

//==================================== To test findAllByAccommodationId method=====================================
    @Test
    @DisplayName("READ - Should return a list with all Reviews by Accommodation ID")
    void findAllByAccommodationId_ValidData_ShouldReturnAllReviewsByAccommodationId() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(reviewDTOS);
        List<ReviewDTO> result = reviewService.findAllByAccommodationId(validAccommodationId);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(reviewDTOS);

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
        verify(reviewDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is null")
    void findAllByAccommodationId_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> reviewService.findAllByAccommodationId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Accommodation ID cannot be null");

        verify(reviewDAO, never()).findAllByAccommodationId(null);
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is less or equal to 0")
    void findAllByAccommodationId_IdLessOrEqualZero_ShouldThrowException() {

        assertThatThrownBy(() -> reviewService.findAllByAccommodationId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Accommodation ID must be greater than 0");

        verify(reviewDAO, never()).findAllByAccommodationId(0L);

        assertThatThrownBy(() -> reviewService.findAllByAccommodationId(-10L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Accommodation ID must be greater than 0");

        verify(reviewDAO, never()).findAllByAccommodationId(-10L);
    }

    @Test
    @DisplayName("READ - Should throw exception when DAO returns null")
    void findAllByAccommodationId_DaoReturnsNull_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> reviewService.findAllByAccommodationId(validAccommodationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reviews not be found");

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
        verify(reviewDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation does not exists")
    void findAllByAccommodationId_NoAccommodationFound_ShouldThrowException() {
        when(accommodationDAO.findById(validAccommodationId)).thenReturn(null);
        assertThatThrownBy(() -> reviewService.findAllByAccommodationId(validAccommodationId)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("Accommodation cannot be found");

        verify(accommodationDAO, times(1)).findById(validAccommodationId);
        verify(reviewDAO, never()).findAllByAccommodationId(validAccommodationId);
    }


//==================================== To test findAllByUserId method=====================================

    @Test
    @DisplayName("READ - Should return a list with all Reviews by User ID")
    void findAllByUserId_ValidData_ShouldReturnAllReviewsByUserId() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        when(reviewDAO.findAllByUserId(validUserId)).thenReturn(reviewDTOS);
        List<ReviewDTO> result = reviewService.findAllByUserId(validUserId);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(reviewDTOS);

        verify(userDAO, times(1)).findById(validUserId);
        verify(reviewDAO, times(1)).findAllByUserId(validUserId);
    }

    @Test
    @DisplayName("READ - Should throw exception when User ID is null")
    void findAllByUserId_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> reviewService.findAllByUserId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User ID cannot be null");

        verify(reviewDAO, never()).findAllByUserId(null);
    }

    @Test
    @DisplayName("READ - Should throw exception when User ID is less or equal to 0")
    void findAllByUserId_IdLessOrEqualZero_ShouldThrowException() {

        assertThatThrownBy(() -> reviewService.findAllByUserId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User ID must be greater than 0");

        verify(reviewDAO, never()).findAllByUserId(0L);

        assertThatThrownBy(() -> reviewService.findAllByUserId(-10L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User ID must be greater than 0");

        verify(reviewDAO, never()).findAllByUserId(-10L);
    }

    @Test
    @DisplayName("READ - Should throw exception when DAO returns null")
    void findAllByUserId_DaoReturnsNull_ShouldThrowException() {

        when(userDAO.findById(validUserId)).thenReturn(validUserDTO);
        when(reviewDAO.findAllByUserId(validUserId)).thenReturn(null);

        assertThatThrownBy(() -> reviewService.findAllByUserId(validUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reviews not be found");

        verify(userDAO, times(1)).findById(validUserId);
        verify(reviewDAO, times(1)).findAllByUserId(validUserId);
    }

//==================================== To test getAverageRatingForAccommodation method=====================================

    @Test
    @DisplayName("READ - Should return average rating for Accommodation by ID")
    void getAverageRatingForAccommodation_ValidData_ShouldReturnAverageRating() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(reviewDTOS);

        double result = reviewService.getAverageRatingForAccommodation(validAccommodationId);

        assertThat(result).isEqualTo(accumulator);

        verify(accommodationDAO, times(2)).findById(validAccommodationId);
        verify(reviewDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is null")
    void getAverageRatingForAccommodation_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> reviewService.getAverageRatingForAccommodation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Accommodation ID cannot be null");

        verify(reviewDAO, never()).findAllByAccommodationId(null);
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is less or equal to 0")
    void getAverageRatingForAccommodation_IdLessOrEqualZero_ShouldThrowException() {

        assertThatThrownBy(() -> reviewService.getAverageRatingForAccommodation(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Accommodation ID must be greater than 0");

        verify(reviewDAO, never()).findAllByAccommodationId(0L);

        assertThatThrownBy(() -> reviewService.getAverageRatingForAccommodation(-10L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Accommodation ID must be greater than 0");

        verify(reviewDAO, never()).findAllByAccommodationId(-10L);
    }

    @Test
    @DisplayName("READ - Should throw exception when DAO returns null")
    void getAverageRatingForAccommodation_DaoReturnsNull_ShouldThrowException() {

        when(accommodationDAO.findById(validAccommodationId)).thenReturn(validAccommodationDTO);
        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> reviewService.getAverageRatingForAccommodation(validAccommodationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reviews not be found");

        verify(accommodationDAO, times(2)).findById(validAccommodationId);
        verify(reviewDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }
}
