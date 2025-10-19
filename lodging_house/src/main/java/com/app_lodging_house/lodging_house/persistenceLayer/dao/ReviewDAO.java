package com.app_lodging_house.lodging_house.persistenceLayer.dao;

import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewCreateDTO;
import com.app_lodging_house.lodging_house.bussinessLayer.dto.ReviewDTO;
import com.app_lodging_house.lodging_house.persistenceLayer.entity.ReviewEntity;
import com.app_lodging_house.lodging_house.persistenceLayer.mapper.ReviewMapper;
import com.app_lodging_house.lodging_house.persistenceLayer.repository.ReviewReposityory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDAO {
    private final ReviewMapper reviewMapper;
    private final ReviewReposityory reviewRepository;

    public ReviewDTO save(ReviewCreateDTO dto){
        ReviewEntity entity = reviewMapper.cDtoToEntity(dto);
        ReviewEntity saved = reviewRepository.save(entity);

        return reviewMapper.toDTO(saved);
    }

    public List<ReviewDTO> findAllByAccommodationId(Long id){
        List<ReviewEntity> entities = reviewRepository.findAllByAccommodationId(id);
        List<ReviewDTO> dtos = new ArrayList<>();
        for (ReviewEntity entity : entities) {
            dtos.add(reviewMapper.toDTO(entity));
        }
        return dtos;
    }

    public List<ReviewDTO> findAllByUserId(Long id){
        List<ReviewEntity> entities = reviewRepository.findAllByUserId(id);
        List<ReviewDTO> dtos = new ArrayList<>();
        for (ReviewEntity entity : entities) {
            dtos.add(reviewMapper.toDTO(entity));
        }
        return dtos;
    }

}
