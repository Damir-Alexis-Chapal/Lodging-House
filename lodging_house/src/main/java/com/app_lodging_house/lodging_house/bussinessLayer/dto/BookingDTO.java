package com.app_lodging_house.lodging_house.bussinessLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Booking info")
public class BookingDTO {
    @Schema(description = "Booking ID", example = "1")
    private Long id;

    @Schema(description = "The Accommodation's id", example = "1")
    private Long accommodationId;

    @Schema(description = "The user's ID", example = "1")
    private Long ownerId;

    @Schema(description = "Date check in", example = "2025-10-04 17:46:59.128654")
    private LocalDate dateCheckIn;

    @Schema(description = "Date check out", example = "2025-10-04 17:46:59.128654")
    private LocalDate dateCheckOut;

    @Schema(description = "Guests number", example = "5")
    private int guestsNumber;

    @Schema(description = "The booking status", example = "PENDING")
    private String status;
}
