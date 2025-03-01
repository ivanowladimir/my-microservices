package ru.ivanov.loanservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanov.loanservice.dto.LoanDecisionDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {
    private final KafkaProducerService kafkaProducerService;

    public void processLoanDecision(LoanDecisionDTO loanDecision) {
        // Отправляем объект напрямую, без преобразования в JSON
        kafkaProducerService.sendLoanDecision(loanDecision);
        log.info("Заявка обработана и отправлена в Kafka: {}", loanDecision);
    }
}