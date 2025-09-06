package com.app_lodging_house.lodging_house.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
// This annotation is for the OpenApi documentation
@Tag(name = "Auth", description = "Operations related to authentication and sessions")
// ↑ This will gather all endpoints in this class as "Auth" in Swagger
public class AuthController {
}
