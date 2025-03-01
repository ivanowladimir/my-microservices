package ru.ivanov.creditservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.ivanov.creditservice.entity.CreditRequest;
import ru.ivanov.creditservice.repository.CreditRepository;
import ru.ivanov.creditservice.dto.LoanDecisionDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final CreditRepository creditRepository;

    @KafkaListener(topics = "credit_confirmation", groupId = "credit-group")
    public void listenCreditConfirmation(LoanDecisionDTO message) {
        log.info("Получено сообщение от Kafka: {}", message);

        // Находим последнюю заявку пользователя со статусом PENDING
        Optional<CreditRequest> optionalRequest = creditRepository
                .findTopByUserIdAndStatusOrderByCreatedAtDesc(message.getUserId(), "PENDING");

        if (optionalRequest.isPresent()) {
            CreditRequest creditRequest = optionalRequest.get();
            creditRequest.setStatus(message.getStatus());
            creditRepository.save(creditRequest);
            log.info("Обновлена заявка: {}", creditRequest);
        } else {
            log.warn("Заявка со статусом PENDING не найдена для userId: {}", message.getUserId());
        }
    }
}