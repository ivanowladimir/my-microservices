package ru.ivanov.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.userservice.dto.AuthRequestDTO;
import ru.ivanov.userservice.dto.AuthResponseDTO;
import ru.ivanov.userservice.security.JwtUtils;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO request) {
        log.info("User Service: Запрос на логин от {}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
        log.info("User Service: Логин успешен, токен сгенерирован");

        return ResponseEntity.ok(new AuthResponseDTO(jwt, "Успешная аутентификация", request.getUsername()));
    }
}
