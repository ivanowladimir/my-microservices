package ru.ivanov.loanservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ivanov.loanservice.dto.LoanDecisionDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, LoanDecisionDTO> kafkaTemplate;

    private static final String TOPIC = "credit_confirmation";

    public void sendLoanDecision(LoanDecisionDTO decision) {
        log.info("Отправка решения в Kafka: {}", decision);
        kafkaTemplate.send(TOPIC, decision);
    }
}