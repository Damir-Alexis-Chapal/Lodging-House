package com.app_lodging_house.lodging_house.presentationLayer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Operations related to user authentication and sessions")
// ↑ This will gather all endpoints in this class as "Auth" in Swagger
public class AuthController {
    //All endpoints return a String ResponseEntity, I made it like this since those endpoints have
    //not the real logic and are just examples
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    200 OK → operation successful (login, logout, refresh, get current user).
    400 Bad Request → invalid login data.
    401 Unauthorized → invalid credentials, expired token, or user not authenticated.
    500 Internal Server Error → unexpected error.*/
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 1: LOGIN
    @Operation(
            summary = "User login",
            description = "Authenticates user with email and password, returns access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid login data"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @Parameter(description = "Email", example = "johndoe@example.com") @RequestParam String email,
            @Parameter(description = "Password", example = "123456") @RequestParam String password) {
        //This is just an example,it's not the real logic, we don't know how this would work yet
        return new ResponseEntity<>("JWT_TOKEN_EXAMPLE", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: LOGOUT
    @Operation(
            summary = "User logout",
            description = "Invalidates the current user session or JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: REFRESH TOKEN
    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using a valid refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(
            @Parameter(description = "Refresh token", example = "REFRESH_TOKEN_EXAMPLE")
            @RequestParam String refreshToken) {
        //This is just an example,it's not the real logic, I'm not sure if this would even be used
        return new ResponseEntity<>("NEW_JWT_TOKEN_EXAMPLE", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: GET CURRENT USER
    @Operation(
            summary = "Get current user",
            description = "Returns details of the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data returned"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("User details (example)", HttpStatus.OK);
    }
}
