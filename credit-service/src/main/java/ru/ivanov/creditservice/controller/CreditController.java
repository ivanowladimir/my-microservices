package ru.ivanov.creditservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.creditservice.dto.CreditRequestDTO;
import ru.ivanov.creditservice.entity.CreditRequest;
import ru.ivanov.creditservice.security.JwtUtil;
import ru.ivanov.creditservice.service.CreditService;

import java.util.List;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
@Slf4j
public class CreditController {

    private final CreditService creditService;
    private final JwtUtil jwtUtil;

    @PostMapping("/apply")
    public ResponseEntity<?> applyForCredit(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CreditRequestDTO creditRequestDTO) {

        log.info("Получен запрос на кредитную заявку от пользователя с токеном: {}", authHeader);

        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractUserId(token);

        log.info("Извлечённый userId из токена: {}", userId);

        try {
            CreditRequest creditRequest = creditService.processCreditRequest(userId, creditRequestDTO);
            log.info("Кредитная заявка успешно обработана для userId: {}", userId);
            return ResponseEntity.ok(creditRequest);
        } catch (IllegalStateException e) {
            log.warn("Ошибка: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<CreditRequest>> getUserCredits(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractUserId(token);

        log.info("Получение кредитных заявок для userId: {}", userId);
        List<CreditRequest> credits = creditService.getUserCredits(userId);

        return ResponseEntity.ok(credits);
    }
}