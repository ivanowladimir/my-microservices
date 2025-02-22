package ru.ivanov.creditservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ivanov.creditservice.entity.CreditRequest;

@Service
@RequiredArgsConstructor
public class CreditEventProducer {
    private final KafkaTemplate<String, CreditRequest> kafkaTemplate;

    public void sendCreditRequestEvent(CreditRequest creditRequest) {
        kafkaTemplate.send("credit_requests", creditRequest);
    }
}
