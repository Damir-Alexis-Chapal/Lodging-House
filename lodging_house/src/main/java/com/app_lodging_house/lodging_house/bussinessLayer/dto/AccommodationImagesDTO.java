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
@Schema(description = "Accommodation images info")
public class AccommodationImagesDTO {
    @Schema(description = "img id", example = "1")
    private Long id;

    @NotBlank(message = "The name is required")
    @Schema(description = "Accommodation name", example = "https://es.pinterest.com/pin/627337423126144610/", required = true)
    private String imageUrl;

    @NotNull(message = "The accommodation ID is required")
    @Schema(description = "ID of the accommodation who owns the img", example = "1", required = true)
    private Long accommodationId;

}
