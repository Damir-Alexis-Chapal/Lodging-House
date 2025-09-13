package com.app_lodging_house.lodging_house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accommodations")
@Tag(name = "Accommodations", description = "Operations related to hostings management")
// ↑ This will gather all endpoints in this class as "Accommodations" in Swagger
public class AccommodationController {
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 1: ADD accommodation
    @Operation(summary = "Add a new accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accommodation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> addAccommodation(
            @Parameter(description = "Accommodation name", example = "Beach House") @RequestParam String name,
            @Parameter(description = "City where the accommodation is located", example = "Cartagena") @RequestParam String city,
            @Parameter(description = "Price per night", example = "120") @RequestParam double price,
            @Parameter(description = "Maximum number of guests", example = "4") @RequestParam int maxGuests) {
        //This is just an example,it's not the real logic, we will need some more parameters to create a new accommodation
        return new ResponseEntity<>("Accommodation created successfully", HttpStatus.CREATED);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: UPDATE accommodation
    @Operation(summary = "Update an existing accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccommodation(
            @Parameter(description = "Accommodation ID", example = "1") @PathVariable long id,
            @Parameter(description = "Updated name", example = "Luxury Beach House") @RequestParam String name,
            @Parameter(description = "Updated price", example = "150") @RequestParam double price,
            @Parameter(description = "Updated max guests", example = "6") @RequestParam int maxGuests) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Accommodation updated successfully", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: GET by ID
    @Operation(summary = "Get accommodation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation found"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> getAccommodationById(
            @Parameter(description = "Accommodation ID", example = "1") @PathVariable long id) {

        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Accommodation found", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: GET all
    @Operation(summary = "Get all accommodations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accommodations returned"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<String> getAllAccommodations() {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("List of accommodations returned", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 5: SEARCH by name or city
    @Operation(summary = "Search accommodations by name or city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results returned"),
            @ApiResponse(responseCode = "404", description = "No accommodations found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<String> searchAccommodations(
            @Parameter(description = "Accommodation name", example = "Beach") @RequestParam(required = false) String name,
            @Parameter(description = "City", example = "Cartagena") @RequestParam(required = false) String city) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Search results returned", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 6: FILTER by price range or guests (it's just an example, we will add some more filter options later)
    @Operation(summary = "Filter accommodations by price and number of guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered results returned"),
            @ApiResponse(responseCode = "404", description = "No accommodations match the filter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/filter")
    public ResponseEntity<String> filterAccommodations(
            @Parameter(description = "Minimum price", example = "50") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price", example = "200") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Minimum guests", example = "2") @RequestParam(required = false) Integer minGuests,
            @Parameter(description = "Maximum guests", example = "6") @RequestParam(required = false) Integer maxGuests) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Filtered results returned", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 7: DELETE
    @Operation(summary = "Delete an accommodation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccommodation(
            @Parameter(description = "Accommodation ID", example = "1") @PathVariable long id) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Accommodation deleted successfully", HttpStatus.OK);
    }
}
