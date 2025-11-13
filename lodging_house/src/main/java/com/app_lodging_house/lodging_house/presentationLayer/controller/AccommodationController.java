package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.*;
import com.app_lodging_house.lodging_house.bussinessLayer.service.AccommodationService;
import com.app_lodging_house.lodging_house.bussinessLayer.service.LocationService;
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
@RequestMapping("/api/v1/accommodations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Accommodations", description = "Operations related to hostings management")
// ↑ This will gather all endpoints in this class as "Accommodations" in Swagger
public class AccommodationController {
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    201 Created → accommodation created successfully.
    200 OK → operation successful (get, update, delete, search, filter).
    400 Bad Request → invalid data.
    404 Not Found → accommodation not found or no results.
    500 Internal Server Error → unexpected error.*/
    //------------------------------------------------------------------------------------------------------------------
    private final AccommodationService accommodationService;
    private final LocationService locationService;

    // ENDPOINT 1: ADD accommodation
    @Operation(summary = "Add a new accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accommodation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> addAccommodation(
            @Parameter(description = "Accommodation data required to create a new accommodation", required = true)
            @RequestBody AccommodationCreateDTO dto) {
        try {
            AccommodationDTO createdAccommodation = accommodationService.createAccommodation(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccommodation);
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
    // ENDPOINT 2: UPDATE accommodation
    @Operation(summary = "Update an existing accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccommodation(
            @Parameter(description = "Accommodation ID", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated accommodation data", required = true)
            @RequestBody AccommodationCreateDTO dto) {
        try {
            AccommodationDTO updatedAccommodation = accommodationService.updateAccommodation(id, dto);
            if (updatedAccommodation == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Accommodation with ID " + id + " not found"));
            }
            return ResponseEntity.ok(updatedAccommodation);
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
    // ENDPOINT 3: GET by ID
    @Operation(summary = "Get accommodation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation found"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccommodationById(
            @Parameter(description = "Accommodation ID", example = "1", required = true)
            @PathVariable long id) {
        try {
            AccommodationDTO accommodation = accommodationService.getAccommodationById(id);
            if (accommodation == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Accommodation with ID " + id + " not found"));
            }
            return ResponseEntity.ok(accommodation);
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
    // ENDPOINT 4: GET all
    @Operation(summary = "Get all accommodations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accommodations returned"),
            @ApiResponse(responseCode = "404", description = "No accommodations found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<?> getAllAccommodations() {
        try {
            List<AccommodationDTO> accommodations = accommodationService.getAllAccommodations();
            if (accommodations == null || accommodations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "No accommodations found"));
            }
            return ResponseEntity.ok(accommodations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
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
    public ResponseEntity<?> searchAccommodations(
            @Parameter(description = "Accommodation name", example = "Sol Brillante")
            @PathVariable String name) {
        try {
            AccommodationDTO accommodationDTO = accommodationService.getAccommodationByName(name);
            if (accommodationDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No accommodation found with name: " + name));
            }
            return ResponseEntity.ok(accommodationDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 6: FILTER by price range or guests (it's just an example, we will add some more filter options later)
    @Operation(summary = "Filter accommodations by price and number of guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered results returned successfully"),
            @ApiResponse(responseCode = "404", description = "No accommodations match the provided filters"),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @GetMapping("/filter")
    public ResponseEntity<?> filterAccommodations(
            @Parameter(description = "Minimum price", example = "50") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price", example = "200") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "City", example = "armenia") @RequestParam(required = false) String city,
            @Parameter(description = "Services", example = "wifi, piscina") @RequestParam(required = false) List<String> services) {
        try {
            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", "Minimum price cannot be greater than maximum price"
                ));
            }
            System.out.println(minPrice + " " + maxPrice + " " + city + " " + services);
            List<AccommodationDTO> results = accommodationService.filterAccommodations(minPrice, maxPrice, city, services);

            if (results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "error", "Not Found",
                        "message", "No accommodations match the provided filters"
                ));
            }

            return ResponseEntity.ok(results);

        } catch (IllegalArgumentException e) {
            // Usar mensaje por defecto si e.getMessage() es null
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Invalid request parameters";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", 400,
                    "error", "Bad Request",
                    "message", errorMessage
            ));

        } catch (Exception e) {
            // Usar mensaje por defecto si e.getMessage() es null
            String errorMessage = e.getMessage() != null ? e.getMessage() : "An unexpected error occurred";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", 500,
                    "error", "Internal Server Error",
                    "message", errorMessage
            ));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 7: DELETE
    @Operation(summary = "Delete an accommodation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccommodation(
            @Parameter(description = "Accommodation ID", example = "1")
            @PathVariable long id) {
        try {
            accommodationService.deleteAccommodation(id);
            return ResponseEntity.ok(Map.of("message", "The accommodation has been deleted successfully"));
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
    // ENDPOINT 8: ADD location to existing accommodation
    @Operation(summary = "Add a new location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location was added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/location")
    public ResponseEntity<?> addLocation(
            @Parameter(description = "Location data that we need to create a new Accommodation", required = true)
            @RequestBody LocationCreateDTO dto) {
        try {
            LocationDTO locationDTO = locationService.addLocation(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(locationDTO);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 400,"error", "Bad Request","message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 9: ADD services to existing accommodation
    @Operation(summary = "Add services for Accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Services were added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/services")
    public ResponseEntity<?> assignServices(
            @PathVariable Long id,
            @RequestBody ServiceAssignmentDTO dto) {

        try {
            if (dto == null || dto.getServiceIds() == null || dto.getServiceIds().isEmpty()) {
                return ResponseEntity.badRequest().body("Service list cannot be empty.");
            }
            AccommodationDTO updated = accommodationService.assignServicesToAccommodation(id, dto.getServiceIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
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
    // ENDPOINT 10: ADD images to existing accommodation
    @Operation(summary = "Add images for Accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Images were added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/images")
    public ResponseEntity<?> assignImagesToAccommodation(
            @RequestBody List<AccommodationImagesDTO> images) {

        try {
            if (images == null || images.isEmpty()) {
                return ResponseEntity.badRequest().body("Images list cannot be empty.");
            }
            List<AccommodationImagesDTO> imagesSaved = accommodationService.saveImagesForAccommodation(images);
            return ResponseEntity.ok(imagesSaved);
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
    // ENDPOINT 11: GET all images by accommodation id
    @Operation(summary = "Get all accommodation images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accommodation images returned"),
            @ApiResponse(responseCode = "404", description = "No accommodation images found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/images")
    public ResponseEntity<?> getAllAccommodationImages(
            @Parameter(description = "Accommodation id", required = true)
            @PathVariable Long id) {
        try {
            List<AccommodationImagesDTO> accommodationImages = accommodationService.getAllAccommodationImages(id);
            if (accommodationImages == null || accommodationImages.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "No accommodation images found"));
            }
            return ResponseEntity.ok(accommodationImages);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404,"error", "Not Found","message", e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", 500,"error", "Internal Server Error","message", e.getMessage()));
        }
    }

}
