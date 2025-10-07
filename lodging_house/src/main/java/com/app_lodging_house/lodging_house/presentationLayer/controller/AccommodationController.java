package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.AccommodationDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.AccommodationService;
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

@RestController
@RequestMapping("/api/v1/accommodations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Accommodations", description = "Operations related to hostings management")
// ↑ This will gather all endpoints in this class as "Accommodations" in Swagger
public class AccommodationController {
    //All endpoints return a String ResponseEntity, I made it like this since those endpoints have
    //not the real logic and are just examples
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    201 Created → accommodation created successfully.
    200 OK → operation successful (get, update, delete, search, filter).
    400 Bad Request → invalid data.
    404 Not Found → accommodation not found or no results.
    500 Internal Server Error → unexpected error.*/
    //------------------------------------------------------------------------------------------------------------------
    private final AccommodationService accommodationService;
    // ENDPOINT 1: ADD accommodation
    @Operation(summary = "Add a new accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accommodation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<AccommodationDTO> addAccommodation(
            @Parameter(description = "Accommodation data that we need to create a new Accommodation", required = true)
            @RequestBody AccommodationCreateDTO dto) {
        try {
            AccommodationDTO createdAccommodation = accommodationService.createAccommodation(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccommodation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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
    public ResponseEntity<AccommodationDTO> updateAccommodation(
            @PathVariable Long id,
            @RequestBody AccommodationCreateDTO dto) {
        try {
            AccommodationDTO updatedAccommodation = accommodationService.updateAccommodation(id, dto);
            return ResponseEntity.ok(updatedAccommodation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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
    public ResponseEntity<AccommodationDTO> getAccommodationById(
            @Parameter(description = "Accommodation ID", example = "1", required = true) @PathVariable long id) {
        try {
            AccommodationDTO accommodation = accommodationService.getAccommodationById(id);
            return ResponseEntity.ok(accommodation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: GET all
    @Operation(summary = "Get all accommodations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accommodations returned"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        List<AccommodationDTO> accommodationDTOS = accommodationService.getAllAccommodations();
        return ResponseEntity.ok(accommodationDTOS);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 5: SEARCH by name
    @Operation(summary = "Search accommodations by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results returned"),
            @ApiResponse(responseCode = "404", description = "No accommodations found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search/{name}")
    public ResponseEntity<AccommodationDTO> searchAccommodations(
            @Parameter(description = "Accommodation name", example = "sol brillante") @PathVariable String name) {
        AccommodationDTO accommodationDTO = accommodationService.getAccommodationByName(name);
        return ResponseEntity.ok(accommodationDTO);
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
        try {
            accommodationService.deleteAccommodation(id);
            return ResponseEntity.ok("The accommodation has been deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
