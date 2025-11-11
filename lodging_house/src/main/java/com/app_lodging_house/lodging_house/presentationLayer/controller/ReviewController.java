package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.ReviewService;
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
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reviews", description = "Operations related to reviews management")
// ↑ This will gather all endpoints in this class as "Reviews" in Swagger
public class ReviewController {
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    201 Created → when creating a review.
    200 OK → for GET and PUT that return data.
    204 No Content → for successful DELETE.
    400 Bad Request → invalid data (rating out of range, incomplete payload).
    401 Unauthorized → if the user is not authenticated or is not the owner (must be handled securely).
    404 Not Found → resource not found (e.g., review or accommodation does not exist in your logic).
    500 Internal Server Error → unexpected errors.*/
    //------------------------------------------------------------------------------------------------------------------
    private final ReviewService reviewService;
    // ENDPOINT 1: TO ADD a review
    @Operation(summary = "Add a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input (e.g. rating out of range or missing fields)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (user must be logged in)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> addReview(
            @Parameter(description = "Review data that we need to create a new one", required = true)
            @RequestBody ReviewCreateDTO dto) {
        try{
            ReviewDTO reviewDTO = reviewService.createReview(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 400,"error", "Bad Request","message", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", 401,"error", "Unauthorized","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: GET all reviews for accommodation
    @Operation(summary = "Get all reviews for an accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews returned (may be empty)"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getReviewsByAccommodation(
            @Parameter(description = "Accommodation id", required = true)
            @PathVariable Long id) {
        try{
            List<ReviewDTO> dtos = reviewService.findAllByAccommodationId(id);
            return ResponseEntity.ok(dtos);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: Get rating for accommodation
    @Operation(summary = "Get average rating for an accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average rating returned (0.0 if no reviews)"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/accommodation/{id}/average")
    public ResponseEntity<?> getAverageRating(
            @Parameter(description = "Accommodation id", required = true)
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(reviewService.getAverageRatingForAccommodation(id));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: Get all reviews for user
    @Operation(summary = "Get all reviews for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews returned (may be empty)"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getReviewsByUser(
            @Parameter(description = "User id", required = true)
            @PathVariable Long id) {
        try{
            List<ReviewDTO> dtos = reviewService.findAllByUserId(id);
            return ResponseEntity.ok(dtos);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }

}
