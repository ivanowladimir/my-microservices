package ru.ivanov.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.userservice.dto.UserResponseDTO;
import ru.ivanov.userservice.repository.UserMainRepository;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserMainRepository userMainRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        log.info("Поиск пользователя с ID: {}", id);
        return userMainRepository.findById(id)
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user)))
                .orElseGet(() -> {
                    log.warn("Пользователь с ID {} не найден", id);
                    return ResponseEntity.notFound().build();
                });
    }
}