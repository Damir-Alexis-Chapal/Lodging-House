package com.app_lodging_house.lodging_house.bussinessLayer.service.impl;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.service.ReviewService;
import com.app_lodging_house.lodging_house.persistenceLayer.dao.ReviewDAO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    @Override
    public ReviewDTO createReview(ReviewCreateDTO dto){
        if(dto==null){
            throw new IllegalArgumentException("Review DTO cannot be null");
        }
        if(dto.getRate()>5 || dto.getRate()<1){
            throw new IllegalArgumentException("The rate must be between 1 and 5");
        }
        ReviewDTO reviewDTO = reviewDAO.save(dto);
        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> findAllByAccommodationId(Long id){
        if(id==null){
            throw new IllegalArgumentException("Accommodation ID cannot be null");
        }
        if(id<=0L){
            throw new IllegalArgumentException("Accommodation ID must be greater than 0");
        }
        List<ReviewDTO> dtos = reviewDAO.findAllByAccommodationId(id);

        if(dtos==null){
            throw new IllegalArgumentException("Reviews not be found");
        }

        return dtos;
    }

    @Override
    public List<ReviewDTO> findAllByUserId(Long id){
        if(id==null){
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if(id<=0L){
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
        List<ReviewDTO> dtos = reviewDAO.findAllByUserId(id);
        if(dtos==null){
            throw new IllegalArgumentException("Reviews not be found");
        }
        return dtos;
    }

    @Override
    public double getAverageRatingForAccommodation(Long id) {
        if(id==null){
            throw new IllegalArgumentException("Accommodation ID cannot be null");
        }
        if(id<=0L){
            throw new IllegalArgumentException("Accommodation ID must be greater than 0");
        }
        List<ReviewDTO> dtos = findAllByAccommodationId(id);
        if(dtos==null){
            throw new IllegalArgumentException("Reviews not be found");
        }
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
