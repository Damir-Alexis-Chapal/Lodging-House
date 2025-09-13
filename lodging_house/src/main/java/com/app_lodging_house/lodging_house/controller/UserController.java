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
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operations related to users management")
// ↑ This will gather all endpoints in this class as "Users" in Swagger
public class UserController {
    //All endpoints return a String ResponseEntity, I made it like this since those endpoints have
    //not the real logic and are just examples
    //------------------------------------------------------------------------------------------------------------------
    /* Response codes used in this controller
    201 Created → When a user was created.
    200 OK → Correct queries and updates.
    204 No Content → When deleting a user is a success.
    400 Bad Request → When input is invalid.
    404 Not Found → When the user doesn't exist.
    500 Internal Server Error → unexpected errors.*/
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
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: GET by ID
    @Operation(summary = "Get a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("User found", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: GET all users
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users returned (may be empty)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("List of users returned", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 4: UPDATE user by ID
    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUSer( @Parameter(description = "User ID",
                                              required = true, example = "1")
                                              @RequestParam Long id){
        //This is just an example,it's not the real logic
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 5: DELETE user by ID
    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "User ID", required = true, example = "1") @PathVariable Long id) {
        //This is just an example,it's not the real logic
       return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
