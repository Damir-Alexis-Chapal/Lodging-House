package com.app_lodging_house.lodging_house.unit.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.impl.BookingServiceImpl;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.BookingDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookingService - Unit Tests")
public class BookingServiceTest {

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookingServiceImpl bookingService;

//==================================== To test createBooking method=====================================

    @Test
    @DisplayName("CREATE - Should return created booking")
    void createBooking_ValidData_ShouldReturnCreatedBooking() {

        LocalDate dateCheckIn = LocalDate.parse("2025-10-04");
        LocalDate dateCheckOut = LocalDate.parse("2025-10-15");

        BookingCreateDTO bookingDTO = new BookingCreateDTO(
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5
        );
        BookingDTO createdBooking = new BookingDTO(
                1L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );

        when(bookingDAO.save(bookingDTO)).thenReturn(createdBooking);
        BookingDTO result = bookingService.createBooking(bookingDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(createdBooking.getId());

        verify(bookingDAO, times(1)).save(bookingDTO);
    }

    @Test
    @DisplayName("CREATE - Should throw exception when BookingDTO is null")
    void createBooking_NullDTO_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.createBooking(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking DTO cannot be null");

        verify(bookingDAO, never()).save(any());
    }

//==================================== To test getBookingByID method=====================================

    @Test
    @DisplayName("READ - Should return a BookingDTO by ID")
    void getBookingById_ValidId_ShouldReturnBookingDTO() {
        LocalDate dateCheckIn = LocalDate.parse("2025-10-04");
        LocalDate dateCheckOut = LocalDate.parse("2025-10-15");

        BookingDTO existing = new BookingDTO(
                1L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );

        when(bookingDAO.findById(existing.getId())).thenReturn(existing);
        BookingDTO result = bookingService.getBookingById(existing.getId());
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existing.getId());

        verify(bookingDAO, times(1)).findById(existing.getId());
    }

    @Test
    @DisplayName("READ - Should throw exception when Booking ID is null")
    void getBookingById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.getBookingById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Booking ID cannot be null");
        verify(bookingDAO, never()).findById(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when Booking ID is less than or equal to zero")
    void getBookingById_InvalidId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.getBookingById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking ID cannot be negative or zero");

        assertThatThrownBy(() -> bookingService.getBookingById(-5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking ID cannot be negative or zero");

        verify(bookingDAO, never()).findById(anyLong());
    }

//==================================== To test getByUserId method=====================================

    @Test
    @DisplayName("READ - Should return a List of BookingDTO")
    void getBookingById_ValidId_ShouldReturnListOfBookingDTO() {
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        LocalDate dateCheckIn = LocalDate.parse("2025-10-04");
        LocalDate dateCheckOut = LocalDate.parse("2025-10-15");

        BookingDTO existing = new BookingDTO(
                1L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );
        BookingDTO existing2 = new BookingDTO(
                2L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );
        bookingDTOList.add(existing);
        bookingDTOList.add(existing2);

        when(bookingDAO.findByUserId(existing.getId())).thenReturn(bookingDTOList);

        List<BookingDTO> result = bookingService.getByUserId(existing.getId());
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bookingDTOList);

        verify(bookingDAO, times(1)).findByUserId(existing.getId());
    }

    @Test
    @DisplayName("READ - Should throw exception when User ID is null")
    void getByUserId_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.getByUserId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User ID cannot be null");

        verify(bookingDAO, never()).findByUserId(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when User ID is negative")
    void getByUserId_NegativeId_ShouldThrowException() {

        assertThatThrownBy(() -> bookingService.getByUserId(-5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User ID cannot be negative or zero");

        verify(bookingDAO, never()).findByUserId(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when User ID is zero")
    void getByUserId_ZeroId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.getByUserId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User ID cannot be negative or zero");

        verify(bookingDAO, never()).findByUserId(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when no bookings found for User ID")
    void getByUserId_NotFound_ShouldThrowException() {

        when(bookingDAO.findByUserId(1L)).thenReturn(null);

        assertThatThrownBy(() -> bookingService.getByUserId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking not be found");

        verify(bookingDAO, times(1)).findByUserId(1L);
    }

//==================================== To test getByAccommodationId method=====================================
    @Test
    @DisplayName("READ - Should return Booking list by Accommodation ID")
    void getByAccommodationId_ValidId_ShouldReturnBookingList() {
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        LocalDate dateCheckIn = LocalDate.parse("2025-10-04");
        LocalDate dateCheckOut = LocalDate.parse("2025-10-15");

        BookingDTO existing = new BookingDTO(
                1L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );
        BookingDTO existing2 = new BookingDTO(
                2L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );
        bookingDTOList.add(existing);
        bookingDTOList.add(existing2);

        when(bookingDAO.findByAccommodationId(existing.getAccommodationId())).thenReturn(bookingDTOList);

        List<BookingDTO> result = bookingService.getByAccommodationId(existing.getAccommodationId());
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bookingDTOList);

        verify(bookingDAO, times(1)).findByAccommodationId(existing.getAccommodationId());
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is null")
    void getByAccommodationId_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.getByAccommodationId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation ID cannot be null");

        verify(bookingDAO, never()).findByAccommodationId(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is negative")
    void getByAccommodationId_NegativeId_ShouldThrowException() {

        assertThatThrownBy(() -> bookingService.getByAccommodationId(-5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation ID cannot be negative or zero");

        verify(bookingDAO, never()).findByAccommodationId(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is zero")
    void getByAccommodationId_ZeroId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.getByAccommodationId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Accommodation ID cannot be negative or zero");

        verify(bookingDAO, never()).findByAccommodationId(anyLong());
    }

    @Test
    @DisplayName("READ - Should throw exception when no bookings found for Accommodation ID")
    void getByAccommodationId_NotFound_ShouldThrowException() {

        when(bookingDAO.findByAccommodationId(1L)).thenReturn(null);

        assertThatThrownBy(() -> bookingService.getByAccommodationId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking not be found");

        verify(bookingDAO, times(1)).findByAccommodationId(1L);
    }

//==================================== To test cancelBooking method=====================================

    @Test
    @DisplayName("UPDATE - Should cancel Booking by ID and return it")
    void cancelBookingById_ValidId_ShouldReturnBooking() {
        LocalDate dateCheckIn = LocalDate.parse("2025-10-04");
        LocalDate dateCheckOut = LocalDate.parse("2025-10-15");
        BookingDTO existing = new BookingDTO(
                1L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
                "PENDING"
        );
        BookingDTO canceled = new BookingDTO(
                1L,
                1L,
                1L,
                dateCheckIn,
                dateCheckOut,
                5,
            "CANCELED"
        );

        when(bookingDAO.saveCancelledBooking(existing.getId())).thenReturn(canceled);
        BookingDTO result = bookingService.cancelBooking(existing.getId());
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(canceled);

        verify(bookingDAO, times(1)).saveCancelledBooking(existing.getId());
    }

    @Test
    @DisplayName("READ - Should throw exception when Accommodation ID is null")
    void cancelBooking_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> bookingService.cancelBooking(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking ID cannot be null");

        verify(bookingDAO, never()).saveCancelledBooking(anyLong());
    }

    @Test
    @DisplayName("CANCEL - Should throw exception when Booking ID is negative")
    void cancelBooking_NegativeId_ShouldThrowException() {
        Long invalidId = -10L;

        assertThatThrownBy(() -> bookingService.cancelBooking(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking ID cannot be negative or zero");


        verify(bookingDAO, never()).saveCancelledBooking(anyLong());
    }

    @Test
    @DisplayName("CANCEL - Should throw exception when Booking ID is zero")
    void cancelBooking_ZeroId_ShouldThrowException() {
        Long invalidId = 0L;

        assertThatThrownBy(() -> bookingService.cancelBooking(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking ID cannot be negative or zero");

        verify(bookingDAO, never()).saveCancelledBooking(anyLong());
    }

    @Test
    @DisplayName("CANCEL - Should throw exception when Booking not found")
    void cancelBooking_NotFound_ShouldThrowException() {
        Long validId = 5L;

        when(bookingDAO.saveCancelledBooking(validId)).thenReturn(null);

        assertThatThrownBy(() -> bookingService.cancelBooking(validId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Booking not be found");

        verify(bookingDAO, times(1)).saveCancelledBooking(validId);
    }

}
