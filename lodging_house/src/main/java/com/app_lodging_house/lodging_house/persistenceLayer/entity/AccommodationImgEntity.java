package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//id SERIAL PRIMARY KEY,
//accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
//image_url TEXT

@Entity
@Table(name = "accommodation_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private AccommodationEntity accommodation;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

}
