package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.BookingDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Bookings", description = "Operations related to booking management")
// ↑ This will gather all endpoints in this class as "Bookings" in Swagger
public class BookingController {
    //All endpoints return a String ResponseEntity, I made it like this since those endpoints have
    //not the real logic and are just examples
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    201 Created → booking created successfully.
    200 OK → operation successful (get bookings by id, user, accommodation).
    400 Bad Request → invalid booking data.
    404 Not Found → booking not found or no results.
    500 Internal Server Error → unexpected error.*/
    //------------------------------------------------------------------------------------------------------------------
    private final BookingService bookingService;
    // ENDPOINT 1: CREATE BOOKING
    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createBooking(
            @Parameter(description = "Booking data used to create a new Booking", required = true)
            @RequestBody BookingCreateDTO dto) {
        try {
            BookingDTO createdBooking = bookingService.createBooking(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 400,"error", "Bad Request","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()
                    ));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: GET BOOKING BY ID
    @Operation(summary = "Get a booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "400", description = "Invalid booking ID"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(
            @Parameter(description = "Booking ID", required = true, example = "1")
            @PathVariable long id) {
        try {
            BookingDTO booking = bookingService.getBookingById(id);
            return ResponseEntity.ok(booking);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 400,"error", "Bad Request","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: GET BOOKINGS BY USER
    @Operation(summary = "Get all bookings by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "404", description = "No bookings found for this user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(
            @Parameter(description = "User ID", required = true, example = "1") @PathVariable long userId) {
        try {
            List<BookingDTO> bookings = bookingService.getByUserId(userId);
            return ResponseEntity.ok(bookings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 404,"error", "Bad Request","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }

    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: GET BOOKINGS BY ACCOMMODATION ID
    @Operation(summary = "Get all bookings by accommodation ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "404", description = "No bookings found for this accommodation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<?> getAllBookingsByAccommodationId(
            @Parameter(description = "Accommodation ID", required = true, example = "10")
            @PathVariable long accommodationId) {
        try {
            List<BookingDTO> bookings = bookingService.getByAccommodationId(accommodationId);
            return ResponseEntity.ok(bookings);
        }  catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 5: TO CANCEL BOOKINGS BY ID
    @Operation(summary = "Cancel an existing booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(
            @PathVariable Long id) {
        try {
            BookingDTO bookingCancelled = bookingService.cancelBooking(id);
            return ResponseEntity.ok(bookingCancelled);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 400,"error", "Bad Request","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }

}
