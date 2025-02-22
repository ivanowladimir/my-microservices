package ru.ivanov.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_main", schema = "user_db")
@Data
public class UserMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "userMain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // 🔥 Отключает сериализацию userPersonal в JSON
    private UserPersonal userPersonal;

    @OneToOne(mappedBy = "userMain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // 🔥 Отключает сериализацию userContact в JSON
    private UserContact userContact;

    @OneToOne(mappedBy = "userMain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // 🔥 Отключает сериализацию userAddress в JSON
    private UserAddress userAddress;
}
