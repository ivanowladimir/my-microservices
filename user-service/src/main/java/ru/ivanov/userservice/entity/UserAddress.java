package ru.ivanov.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_address", schema = "user_db")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;   // Страна
    private String city;      // Город
    private String street;    // Улица
    private String house;     // Дом
    private boolean isActive; // Актуальность адреса

    @OneToOne
    @JoinColumn(name = "user_main_id")
    private UserMain userMain;
}