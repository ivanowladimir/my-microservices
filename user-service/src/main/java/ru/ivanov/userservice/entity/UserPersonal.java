package ru.ivanov.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_personal", schema = "user_db")
public class UserPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_main_id")
    private UserMain userMain;
}
