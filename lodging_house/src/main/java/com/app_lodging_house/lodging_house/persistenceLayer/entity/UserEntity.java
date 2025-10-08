package com.app_lodging_house.lodging_house.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//id SERIAL PRIMARY KEY,
//name VARCHAR(100) NOT NULL,
//email VARCHAR(100) NOT NULL,
//password VARCHAR(255) NOT NULL,
//phone_number VARCHAR(20) NOT NULL,
//birth_date DATE NOT NULL,
//profile_img TEXT,
//personal_description TEXT,
//created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable= false, length = 100)
    private String name;

    @Column(name = "email", nullable= false, length = 100)
    private String email;

    @Column(name = "password", nullable= false, length = 255)
    private String password;

    @Column(name = "phone_number", nullable= false, length = 20)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "personal_description")
    private String personalDescription;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

}
