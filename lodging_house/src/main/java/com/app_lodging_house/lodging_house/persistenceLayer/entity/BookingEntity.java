package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

//id SERIAL PRIMARY KEY,
//accommodation_id INT REFERENCES accommodations(id) ON DELETE CASCADE,
//user_id INT REFERENCES users(id) ON DELETE CASCADE,
//date_check_in DATE NOT NULL,
//date_check_out DATE NOT NULL,
//CHECK (date_check_out > date_check_in),
//guests_number INT NOT NULL CHECK (guests_number>0),
//status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
//created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

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

    @Column(name = "date_check_in", nullable = false)
    private LocalDate dateCheckIn;

    @Column(name = "date_check_out", nullable = false)
    private LocalDate dateCheckOut;

    @Column(name = "guests_number", nullable = false)
    private int guestsNumber;

    @Column(name = "status", nullable = false)
    private String status;

}
