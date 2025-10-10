package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.ReviewService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.ReviewDAO;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.ReviewMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.ReviewReposityory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    @Override
    public ReviewDTO createReview(ReviewDTO dto){
        ReviewDTO reviewDTO = reviewDAO.save(dto);
        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> findAllByAccommodationId(Long id){
        List<ReviewDTO> dtos = reviewDAO.findAllByAccommodationId(id);
        return dtos;
    }

    @Override
    public List<ReviewDTO> findAllByUserId(Long id){
        List<ReviewDTO> dtos = reviewDAO.findAllByUserId(id);
        return dtos;
    }

    @Override
    public double getAverageRatingForAccommodation(Long id) {
        List<ReviewDTO> dtos = findAllByAccommodationId(id);

        if (dtos.isEmpty()) {
            return 0.0;
        }
        int accumulator = 0;
        for (ReviewDTO dto : dtos) {
            accumulator += dto.getRate();
        }

        return (double) accumulator / dtos.size();
    }


}
