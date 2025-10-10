package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO used to create and share information for reviews")
public class ReviewDTO {

    @Schema(description = "Review ID", example = "1")
    private Long id;

    @Schema(description = "The Accommodation's id", example = "1")
    private Long accommodationId;

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
