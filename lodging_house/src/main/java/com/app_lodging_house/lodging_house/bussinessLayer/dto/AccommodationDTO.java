package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Accommodation info")
public class AccommodationDTO {

    @Schema(description = "Accommodation ID", example = "1", required = true)
    private Long id;

    @Schema(description = "Accommodation name", example = "El solecito", required = true)
    private String name;

    @Schema(description = "Accommodation description", example = "This is a good place to visit because...", required = true)
    private String description;

    @Schema(description = "Price per night", example = "150000", required = true)
    private double price;

    @Schema(description = "The number of persons the place can hold", example = "5", required = true)
    private int maxCapacity;

    @Schema(description = "Whether the place is available", example = "true", required = false)
    private boolean isAvailable;

    @Schema(description = "The owner's ID", example = "1", required = true)
    private Long ownerId;

}
