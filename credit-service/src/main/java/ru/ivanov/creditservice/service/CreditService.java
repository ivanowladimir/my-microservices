package ru.ivanov.creditservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.creditservice.dto.CreditRequestDTO;
import ru.ivanov.creditservice.entity.CreditRequest;
import ru.ivanov.creditservice.repository.CreditRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService {
    private final CreditRepository creditRepository;

    @Transactional
    public CreditRequest processCreditRequest(String userId, CreditRequestDTO requestDTO) {
        log.info("Обработка заявки на кредит для userId: {}", userId);

        CreditRequest creditRequest = CreditRequest.builder()
                .userId(userId)
                .amount(requestDTO.getAmount())
                .term(requestDTO.getTerm())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Создана кредитная заявка: {}", creditRequest);

        CreditRequest savedRequest = creditRepository.save(creditRequest);

        log.info("Кредитная заявка успешно сохранена в БД: {}", savedRequest);

        return savedRequest;
    }

    public List<CreditRequest> getUserCredits(String userId) {
        log.info("Получение кредитных заявок для userId: {}", userId);
        return creditRepository.findByUserId(userId);
    }
}