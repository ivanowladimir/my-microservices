package ru.ivanov.userservice.dto;

import lombok.Data;
import ru.ivanov.userservice.entity.UserMain;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;

    public UserResponseDTO(UserMain user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}