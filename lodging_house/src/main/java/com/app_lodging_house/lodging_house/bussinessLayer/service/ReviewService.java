package com.app_lodging_house.lodging_house.bussinessLayer.service;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    //To create a new review
    ReviewDTO createReview(ReviewDTO dto);
    //To get all review for Accommodation
    List<ReviewDTO> findAllByAccommodationId(Long id);
    //To get all by user id
    List<ReviewDTO> findAllByUserId(Long id);
    //To get the average rating
    double getAverageRatingForAccommodation(Long id);
}
