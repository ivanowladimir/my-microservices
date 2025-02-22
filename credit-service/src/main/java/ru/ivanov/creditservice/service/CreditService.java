package ru.ivanov.creditservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanov.creditservice.dto.CreditRequestDTO;
import ru.ivanov.creditservice.entity.CreditRequest;
import ru.ivanov.creditservice.repository.CreditRepository;
import ru.ivanov.creditservice.security.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService {
    private final CreditRepository creditRepository;
    private final JwtUtil jwtUtil; // Внедряем JwtUtil

    public CreditRequest createCreditRequest(CreditRequestDTO requestDTO, String token) {
        log.info("Создание заявки на кредит. Запрос: {}", requestDTO);

        String userId;
        try {
            userId = jwtUtil.extractUserId(token); // Получаем ID пользователя из токена
        } catch (Exception e) {
            log.error("Ошибка при извлечении userId из токена: {}", e.getMessage());
            throw new RuntimeException("Невалидный токен");
        }

        CreditRequest creditRequest = CreditRequest.builder()
                .userId(userId)
                .amount(requestDTO.getAmount())
                .term(requestDTO.getTerm())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        CreditRequest savedRequest = creditRepository.save(creditRequest);
        log.info("Кредитная заявка успешно создана: {}", savedRequest);
        return savedRequest;
    }

    public CreditRequest processCreditRequest(String userId, CreditRequest creditRequest) {
        log.info("Обработка заявки на кредит для userId: {}", userId);

        creditRequest.setUserId(userId);
        creditRequest.setStatus("PENDING");
        creditRequest.setCreatedAt(LocalDateTime.now());

        CreditRequest savedRequest = creditRepository.save(creditRequest);
        log.info("Заявка успешно сохранена: {}", savedRequest);

        return savedRequest;
    }

    public List<CreditRequest> getUserCredits(String userId) {
        log.info("Получение кредитных заявок для userId: {}", userId);
        return creditRepository.findByUserId(userId);
    }
}