package ru.ivanov.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_contact", schema = "user_db")
public class UserContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    @OneToOne
    @JoinColumn(name = "user_main_id")
    private UserMain userMain;
}
