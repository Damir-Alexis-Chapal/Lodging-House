package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User info")
public class UserDTO {

    @Schema(description = "User ID", example = "1", required = true)
    private Long id;

    @Schema(description = "User name", example = "Alexis chapal", required = true)
    private String name;

    @Schema(description = "User email", example = "alexis@gmail.com", required = true)
    private String email;

    @Schema(description = "User password", example = "asdbi2u", required = true)
    private String password;

    @Schema(description = "User phone number", example = "1234567890", required = true)
    private String phoneNumber;

    @Schema(description = "User birth date", example = "2025-10-10", required = true)
    private LocalDate birthDate;

    @Schema(description = "User profile img", example = "https://personajes-de-ficcion-" +
            "database.fandom.com/es/wiki/Son_Goku_%28Dragon_Ball_Super%29",  required = false)
    private String profileImg;

    @Schema(description = "User personal description", example = "ADOMINATION", required = false)
    private String personalDescription;

}
