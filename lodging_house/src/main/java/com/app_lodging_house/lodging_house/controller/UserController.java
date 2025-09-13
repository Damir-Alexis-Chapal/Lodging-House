package com.app_lodging_house.lodging_house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operations related to users management")
// ↑ This will gather all endpoints in this class as "Users" in Swagger
public class UserController {
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 1: REGISTER USER
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data"),
            @ApiResponse(responseCode = "409", description = "Email already in use"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Parameter(description = "Username", example = "johndoe") @RequestParam String username,
            @Parameter(description = "Email", example = "johndoe@example.com") @RequestParam String email,
            @Parameter(description = "Password", example = "123456") @RequestParam String password) {
        //This is just an example,it's not the real logic, the real users will need some more parameters to be defined
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

}
