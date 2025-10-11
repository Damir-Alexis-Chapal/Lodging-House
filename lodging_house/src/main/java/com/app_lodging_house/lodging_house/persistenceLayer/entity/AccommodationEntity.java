package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

//id SERIAL PRIMARY KEY,
//name VARCHAR(100) NOT NULL,
//description TEXT,
//price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
//max_capacity INT NOT NULL CHECK (max_capacity > 0),
//is_available BOOLEAN DEFAULT TRUE,
//owner_id INT REFERENCES users(id) ON DELETE CASCADE,
//created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

@Entity
@Table(name = "accommodations")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable= false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "accommodation_services",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServicesEntity> services = new HashSet<>();

}
