package ru.ivanov.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.userservice.dto.UserDTO;
import ru.ivanov.userservice.dto.UserResponseDTO;
import ru.ivanov.userservice.entity.UserMain;
import ru.ivanov.userservice.mapper.UserMapper;
import ru.ivanov.userservice.repository.UserMainRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserMainRepository userMainRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserMainRepository userMainRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMainRepository = userMainRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserMain> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("Создание нового пользователя: {}", userDTO.getUsername());
        UserMain user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userMainRepository.save(user);
        log.info("Пользователь {} успешно создан", userDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

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