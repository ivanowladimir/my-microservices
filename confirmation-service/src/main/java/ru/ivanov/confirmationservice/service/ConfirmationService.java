package ru.ivanov.confirmationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanov.confirmationservice.dto.LoanDecisionDTO;
import ru.ivanov.confirmationservice.dto.CreditRequestDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationService {
    private final LoanClient loanClient;

    public void processCreditRequest(CreditRequestDTO request) {
        log.info("Обработка запроса на кредит: {}", request);

        boolean approved = request.isFsspCheckPassed() && request.isKbCheckPassed();
        String status = approved ? "APPROVED" : "REJECTED";

        LoanDecisionDTO message = new LoanDecisionDTO(request.getUserId(), status);
        loanClient.sendLoanDecision(message);

        log.info("Решение {} отправлено в LoanService", status);
    }
}