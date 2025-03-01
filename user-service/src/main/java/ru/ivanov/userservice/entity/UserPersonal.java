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

    private String lastName;      // Фамилия
    private String firstName;     // Имя
    private String middleName;    // Отчество
    private LocalDate birthDate;  // Дата рождения
    private String birthPlace;    // Место рождения

    @OneToOne
    @JoinColumn(name = "user_main_id")
    private UserMain userMain;
}
