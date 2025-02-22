package ru.ivanov.creditservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.creditservice.dto.CreditRequestDTO;
import ru.ivanov.creditservice.entity.CreditRequest;
import ru.ivanov.creditservice.service.CreditService;

import java.util.List;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForCredit(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody CreditRequest creditRequest) {

        creditService.processCreditRequest(userId, creditRequest);
        return ResponseEntity.ok("Кредитная заявка отправлена!");
    }
}

