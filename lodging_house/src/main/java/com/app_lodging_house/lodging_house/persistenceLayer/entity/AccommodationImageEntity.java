package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private AccommodationEntity accommodation;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;
}

