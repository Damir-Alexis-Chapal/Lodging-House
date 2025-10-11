package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.UserDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
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
    private final UserService userService;
    // ENDPOINT 1: REGISTER USER
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data"),
            @ApiResponse(responseCode = "409", description = "Email already in use"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<UserDTO> registerUser(
            @Parameter(description = "User data that we need to create a new Accommodation", required = true)
            @RequestBody UserCreateDTO dto) {
        try {
            UserDTO createdUser = userService.createUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        try {
            UserDTO user = userService.getById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 3: GET all users
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users returned (may be empty)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
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
    public ResponseEntity<UserDTO> updateUSer(
            @Parameter(description = "User ID and user data", required = true)
            @PathVariable Long id,
            @RequestBody UserCreateDTO dto){
        try {
            UserDTO updatedUser = userService.updateUser(id, dto);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

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
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 6: UPDATE default user role (USER) to host role user (HOST)
    @Operation(summary = "Update the role for an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/role/{id}")
    public ResponseEntity<UserDTO> updateUserRole(
            @Parameter(description = "User data", required = true)
            @PathVariable Long id,
            @RequestBody UserCreateDTO dto){
        try {
            UserDTO updatedUser = userService.updateUserRole(id, dto);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
