package com.app_lodging_house.lodging_house.presentationLayer.controller;

import com.app_lodging_house.lodging_house.bussinessLayer.service.CloudinaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/uploads")
@Tag(name = "Uploads", description = "Operations related to images management")
public class UploadController {
    //------------------------------------------------------------------------------------------------------------------
    private final CloudinaryService cloudinaryService;
    // ENDPOINT 1: ADD user profile image
    @PostMapping("/user/profile/{id}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file, "users/", id);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    // ENDPOINT 2: ADD accommodation images
    @PostMapping("/accommodation/images/{id}")
    public ResponseEntity<?> uploadImagesForAccommodation(@RequestParam("file") MultipartFile[] files, @PathVariable Long id) {
        try {
            List<String> imagesUrl = cloudinaryService.uploadImagesForAccommodation(files, "accommodations/", id);
            return ResponseEntity.ok(imagesUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }
}
