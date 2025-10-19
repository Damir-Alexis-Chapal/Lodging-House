package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "DTO used to create a new review")
public class ReviewCreateDTO {

    @NotBlank(message = "The Accommodation's id is required")
    @Schema(description = "The Accommodation's id", example = "1")
    private Long accommodationId;

    @NotBlank(message = "The user's id is required")
    @Schema(description = "The user's id", example = "1")
    private Long userId;

    @NotNull(message = "The rate is required")
    @Min(value = 1, message = "rate should be between 1 and 5")
    @Max(value = 5, message = "rate should be between 1 and 5")
    @Schema(description = "rate number", example = "4")
    private int rate;

    @Schema(description = "The comment", example = "Good service")
    private String comment;
}
