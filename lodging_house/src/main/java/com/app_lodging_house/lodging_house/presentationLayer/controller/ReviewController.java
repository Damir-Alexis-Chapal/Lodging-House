package com.app_lodging_house.lodging_house.presentationLayer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews", description = "Operations related to reviews management")
// ↑ This will gather all endpoints in this class as "Reviews" in Swagger
public class ReviewController {
    //All endpoints return a String ResponseEntity, I made it like this since those endpoints have
    //not the real logic and are just examples
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
    // ENDPOINT 1: TO ADD a review
    @Operation(summary = "Add a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input (e.g. rating out of range or missing fields)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (user must be logged in)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<String> addReview(
            @RequestParam Long accommodationId,
            @RequestParam Integer rate,
            @RequestParam String comment) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Review added successfully",  HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: GET all reviews
    @Operation(summary = "Get all reviews for an accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews returned (may be empty)"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<String> getReviewsByAccommodation(@PathVariable Long id) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("List of reviews returned",  HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: Get rating
    @Operation(summary = "Get average rating for an accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average rating returned (0.0 if no reviews)"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/accommodation/{id}/average")
    public ResponseEntity<String> getAverageRating(@PathVariable Long id) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Average rating returned",  HttpStatus.OK);
    }
}
