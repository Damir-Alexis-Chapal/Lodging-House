package com.app_lodging_house.lodging_house.unit.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.impl.ReviewServiceImpl;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.ReviewDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewDTO validDTO;
    private ReviewCreateDTO validReviewCreateDTO;
    private int validRate;
    private int invalidRateMin;
    private int invalidRateMax;
    private double accumulator;
    private Long validAccommodationId;
    private Long validUserId;
    private List<ReviewDTO> reviewDTOS;

    @BeforeEach
    void setUp(){
        validRate = 1;
        invalidRateMin = 0;
        invalidRateMax = 6;
        validAccommodationId = 1L;
        validUserId = 1L;

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

        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(reviewDTOS);
        List<ReviewDTO> result = reviewService.findAllByAccommodationId(validAccommodationId);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(reviewDTOS);

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

        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> reviewService.findAllByAccommodationId(validAccommodationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reviews not be found");

        verify(reviewDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }

//==================================== To test findAllByUserId method=====================================

    @Test
    @DisplayName("READ - Should return a list with all Reviews by User ID")
    void findAllByUserId_ValidData_ShouldReturnAllReviewsByUserId() {

        when(reviewDAO.findAllByUserId(validUserId)).thenReturn(reviewDTOS);
        List<ReviewDTO> result = reviewService.findAllByUserId(validUserId);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(reviewDTOS);

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

        when(reviewDAO.findAllByUserId(validUserId)).thenReturn(null);

        assertThatThrownBy(() -> reviewService.findAllByUserId(validUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reviews not be found");

        verify(reviewDAO, times(1)).findAllByUserId(validUserId);
    }

//==================================== To test getAverageRatingForAccommodation method=====================================

    @Test
    @DisplayName("READ - Should return average rating for Accommodation by ID")
    void getAverageRatingForAccommodation_ValidData_ShouldReturnAverageRating() {

        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(reviewDTOS);
        double result = reviewService.getAverageRatingForAccommodation(validAccommodationId);

        assertThat(result).isEqualTo(accumulator);

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

        when(reviewDAO.findAllByAccommodationId(validAccommodationId)).thenReturn(null);

        assertThatThrownBy(() -> reviewService.getAverageRatingForAccommodation(validAccommodationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reviews not be found");

        verify(reviewDAO, times(1)).findAllByAccommodationId(validAccommodationId);
    }
}
