package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.AuthService;
import com.app_lodging_house.lodging_house.bussinessLayer.service.JwtService;
import com.app_lodging_house.lodging_house.config.LoginRequest;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth", description = "Operations related to user authentication and sessions")
// ↑ This will gather all endpoints in this class as "Auth" in Swagger
public class AuthController {
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    200 OK → operation successful (login, logout, refresh, get current user).
    400 Bad Request → invalid login data.
    401 Unauthorized → invalid credentials, expired token, or user not authenticated.
    500 Internal Server Error → unexpected error.*/
    //------------------------------------------------------------------------------------------------------------------
    private final AuthService authService;
    private final JwtService jwtService;
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
    public ResponseEntity<?> loginUser(
            @Parameter(description = "LoginRequest which contains email and password ") @RequestBody LoginRequest loginRequest) {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
        try {
            String token = authService.login(email, password);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
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
    public ResponseEntity<?> refreshToken(
            @Parameter(description = "Refresh token", example = "REFRESH_TOKEN_EXAMPLE")
            @RequestParam String refreshToken) {
        try {
            String newAccessToken = jwtService.refreshToken(refreshToken);
            return ResponseEntity.ok(newAccessToken);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
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
    public ResponseEntity<?> getCurrentUser(@RequestParam String token) {
        try {
            UserDTO user = authService.getCurrentUser(token);
            return ResponseEntity.ok(user);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }
}
