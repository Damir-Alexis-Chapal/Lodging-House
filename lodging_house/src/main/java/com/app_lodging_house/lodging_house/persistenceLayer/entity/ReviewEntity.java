package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//id SERIAL PRIMARY KEY,
//accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
//user_id INT REFERENCES users(id) ON DELETE CASCADE,
//rate INT NOT NULL CHECK (rate BETWEEN 1 AND 5),
//comment TEXT NOT NULL,
//created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private AccommodationEntity accommodation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "comment")
    private String comment;

}
