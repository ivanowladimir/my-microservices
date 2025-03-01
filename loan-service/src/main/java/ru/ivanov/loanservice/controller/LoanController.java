package ru.ivanov.loanservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.loanservice.dto.LoanDecisionDTO;
import ru.ivanov.loanservice.service.LoanService;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
@Slf4j
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/decision")
    public ResponseEntity<String> receiveLoanDecision(@RequestBody LoanDecisionDTO loanDecision) {
        log.info("Получено решение о кредите: {}", loanDecision);

        loanService.processLoanDecision(loanDecision);

        return ResponseEntity.ok("Решение принято и отправлено в Kafka");
    }
}