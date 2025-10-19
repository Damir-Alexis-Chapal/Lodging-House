package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to create location information")
public class LocationCreateDTO {

    @NotBlank(message = "City name is required")
    @Schema(description = "City name", example = "Bogotá")
    private String city;

    @NotBlank(message = "Department name is required")
    @Schema(description = "Department name", example = "Cundinamarca")
    private String department;

    @NotNull(message = "Latitude is required")
    @Schema(description = "Latitude coordinate", example = "4.609710")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Schema(description = "Longitude coordinate", example = "-74.081750")
    private Double longitude;

    @NotBlank(message = "Address is required")
    @Schema(description = "Full address", example = "Cra. 7 #32-16")
    private String address;

    @NotNull(message = "Accommodation ID is required")
    @Schema(description = "The accommodation ID associated with this location", example = "5")
    private Long accommodationId;
}
