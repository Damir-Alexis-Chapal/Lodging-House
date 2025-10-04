package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//id SERIAL PRIMARY KEY,
//city VARCHAR(100),
//department VARCHAR(100),
//latitude DECIMAL(9,6),
//longitude DECIMAL(9,6),
//address VARCHAR(255),
//accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "city", nullable = false,  length = 100)
    private String name;

    @Column(name = "department", nullable = false,  length = 100)
    private String department;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "address", nullable = false,  length = 100)
    private String address;

    @OneToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private AccommodationEntity accommodation;

}
