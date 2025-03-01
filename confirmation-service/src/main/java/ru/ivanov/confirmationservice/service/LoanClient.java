package ru.ivanov.confirmationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.ivanov.confirmationservice.dto.LoanDecisionDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanClient {
    private final RestTemplate restTemplate;

    @Value("${loan-service.url}")
    private String loanServiceUrl;

    public void sendLoanDecision(LoanDecisionDTO message) {
        log.info("Отправка HTTP-запроса в LoanService: {}", message);
        restTemplate.postForObject(loanServiceUrl, message, String.class);
    }
}
