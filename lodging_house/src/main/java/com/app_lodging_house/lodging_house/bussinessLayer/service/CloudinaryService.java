package com.app_lodging_house.lodging_house.bussinessLayer.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {
    String uploadImage(MultipartFile file, String owner, Long id);
    List<String> uploadImagesForAccommodation(MultipartFile[] files, String owner, Long id);

}
