package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "DTO used to create a new user")
public class UserCreateDTO {

    @NotBlank(message = "The name is required")
    @Schema(description = "User name", example = "Alexis chapal", required = true)
    private String name;

    @NotBlank(message = "The email is required")
    @Schema(description = "User email", example = "alexis@gmail.com", required = false)
    private String email;

    @Schema(description = "User password", example = "asdbi2u", required = true)
    private String password;

    @NotBlank(message = "The phone number is required")
    @Schema(description = "User phone number", example = "1234567890", required = true)
    private String phoneNumber;

    @NotBlank(message = "The birth date is required")
    @Schema(description = "User birth date", example = "2025-10-10", required = true)
    private LocalDate birthDate;

    @Schema(description = "User profile img", example = "https://personajes-de-ficcion-" +
            "database.fandom.com/es/wiki/Son_Goku_%28Dragon_Ball_Super%29",  required = false)
    private String profileImg;

    @Schema(description = "User personal description", example = "ADOMINATION", required = false)
    private String personalDescription;

}
