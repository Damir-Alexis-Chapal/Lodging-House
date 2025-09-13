package com.app_lodging_house.lodging_house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/passwords")
@Tag(name = "Passwords", description = "Operations related to passwords management")
// ↑ This will gather all endpoints in this class as "Passwords" in Swagger
public class PasswordController {
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 1: CHANGE PASSWORD (authenticated user)
    @Operation(summary = "Change password",
            description = "Allows authenticated users to change their password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/change")
    public ResponseEntity<String> changePassword(
            @Parameter(description = "Old password", example = "oldPass123") @RequestParam String oldPassword,
            @Parameter(description = "New password", example = "newPass456") @RequestParam String newPassword) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: REQUEST RESET PASSWORD (forgot password)
    @Operation(summary = "Request password reset", description = "Sends an email with reset instructions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset instructions sent to email"),
            @ApiResponse(responseCode = "400", description = "Invalid email format"),
            @ApiResponse(responseCode = "404", description = "Email not registered"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/reset/request")
    public ResponseEntity<String> requestPasswordReset(
            @Parameter(description = "User email", example = "user@example.com") @RequestParam String email) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("Password reset instructions sent to email", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: RESET PASSWORD with token
    @Operation(summary = "Reset password with token", description = "Resets password using a valid recovery token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid token or password format"),
            @ApiResponse(responseCode = "401", description = "Expired or unauthorized token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/reset/confirm")
    public ResponseEntity<String> resetPassword(
            @Parameter(description = "Recovery token", example = "RESET_TOKEN_123456") @RequestParam String token,
            @Parameter(description = "New password", example = "newPass456") @RequestParam String newPassword) {
        //This is just an example,it's not the real logic
        //I don't even know what a token is, but google give me this example so...
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

}
