package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "DTO used to create a new accommodation")
public class AccommodationCreateDTO {

    @NotBlank(message = "The name is required")
    @Schema(description = "Accommodation name", example = "El solecito", required = true)
    private String name;

    @Schema(description = "Accommodation description", example = "This is a great place with a mountain view")
    private String description;

    @NotNull(message = "The price is required")
    @PositiveOrZero(message = "The price must be 0 or higher")
    @Schema(description = "Price per night", example = "150000", required = true)
    private Double price;

    @NotNull(message = "The maximum capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 person")
    @Schema(description = "Maximum number of people the accommodation can hold", example = "5", required = true)
    private Integer maxCapacity;

    @Schema(description = "Whether the place is available or not", example = "true")
    private Boolean available = true;

    @NotNull(message = "The owner ID is required")
    @Schema(description = "ID of the user who owns the accommodation", example = "1", required = true)
    private Long ownerId;

}
