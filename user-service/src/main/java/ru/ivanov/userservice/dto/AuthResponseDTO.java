package ru.ivanov.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String token;       // JWT-токен
    private String message;     // Опциональное сообщение ("Успешная аутентификация")
    private String username;    // Имя пользователя
}