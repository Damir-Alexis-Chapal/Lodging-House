package com.app_lodging_house.lodging_house.presentationLayer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/v1/bookings")
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
    // ENDPOINT 1: CREATE BOOKING
    @Operation(
            summary = "Create a new booking",
            description = "Creates a booking for a user, with check-in, check-out and number of guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> createBooking(
            @Parameter(description = "User ID of the booking", example = "1") @RequestParam int userId,
            @Parameter(description = "Check-in date (yyyy-MM-dd)", example = "2025-09-12") @RequestParam Date dateCheckIn,
            @Parameter(description = "Check-out date (yyyy-MM-dd)", example = "2025-09-15") @RequestParam Date dateCheckOut,
            @Parameter(description = "Number of guests", example = "2") @RequestParam int guestsNumber) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Booking created successfully", HttpStatus.CREATED);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: GET BOOKING BY ID
    @Operation(summary = "Get a booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> getBookingById(
            @Parameter(description = "Booking ID", required = true, example = "1")
            @PathVariable long id) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Booking found", HttpStatus.OK);
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
    public ResponseEntity<String> getAllBookingsByUserId(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable long userId) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>( "Bookings found", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: GET BOOKINGS BY ACCOMMODATION
    @Operation(summary = "Get all bookings by accommodation ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "404", description = "No bookings found for this accommodation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<String> getAllBookingsByAccommodationId(
            @Parameter(description = "Accommodation ID", required = true, example = "10")
            @PathVariable long accommodationId) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Bookings found", HttpStatus.OK);
    }
}
