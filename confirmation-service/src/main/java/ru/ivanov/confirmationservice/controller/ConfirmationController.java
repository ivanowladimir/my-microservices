package ru.ivanov.confirmationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.confirmationservice.dto.CreditRequestDTO;
import ru.ivanov.confirmationservice.service.ConfirmationService;

@RestController
@RequestMapping("/api/confirmation")
@RequiredArgsConstructor
@Slf4j
public class ConfirmationController {
    private final ConfirmationService confirmationService;

    @PostMapping("/confirm")
    public ResponseEntity<String> processCredit(@RequestBody CreditRequestDTO request) {
        log.info("Получен запрос на подтверждение кредита: {}", request);
        confirmationService.processCreditRequest(request);
        return ResponseEntity.ok("Запрос обработан");
    }
}