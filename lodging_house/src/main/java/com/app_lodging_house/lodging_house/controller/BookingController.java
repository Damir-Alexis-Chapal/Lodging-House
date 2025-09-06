package com.app_lodging_house.lodging_house.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Bookings", description = "Operations related to booking management")
// ↑ This will gather all endpoints in this class as "Bookings" in Swagger
public class BookingController {
}
