package com.app_lodging_house.lodging_house.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews", description = "Operations related to reviews management")
// ↑ This will gather all endpoints in this class as "Reviews" in Swagger
public class ReviewController {
}
