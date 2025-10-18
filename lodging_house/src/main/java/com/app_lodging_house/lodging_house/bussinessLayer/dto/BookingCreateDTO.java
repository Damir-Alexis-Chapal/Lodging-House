package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to create a new booking")
public class BookingCreateDTO {

    @NotBlank(message = "The Accommodation's id is required")
    @Schema(description = "The Accommodation's id", example = "1")
    private Long accommodationId;

    @NotBlank(message = "The user's id is required")
    @Schema(description = "The user's ID", example = "1")
    private Long ownerId;

    @NotBlank(message = "The date check in is required")
    @Schema(description = "Date check in", example = "2025-10-04")
    private LocalDate dateCheckIn;

    @NotBlank(message = "The date check out is required")
    @Schema(description = "Date check out", example = "2025-10-04")
    private LocalDate dateCheckOut;

    @NotNull(message = "The guests number is required")
    @Min(value = 1, message = "Guests number must be at least 1 person")
    @Schema(description = "Guests number", example = "5")
    private int guestsNumber;

}
