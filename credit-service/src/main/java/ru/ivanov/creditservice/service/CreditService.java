package ru.ivanov.creditservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.creditservice.client.CamundaClient;
import ru.ivanov.creditservice.dto.CreditRequestDTO;
import ru.ivanov.creditservice.entity.CreditRequest;
import ru.ivanov.creditservice.repository.CreditRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService {
    private final CreditRepository creditRepository;
    private final CamundaClient camundaClient;

    @Transactional
    public CreditRequest processCreditRequest(String userId, CreditRequestDTO requestDTO) {
        log.info("Обработка заявки на кредит для userId: {}", userId);

        // Проверяем, есть ли у пользователя уже заявка в статусе "PENDING"
        Optional<CreditRequest> existingRequest = creditRepository
                .findTopByUserIdAndStatusOrderByCreatedAtDesc(userId, "PENDING");

        if (existingRequest.isPresent()) {
            log.warn("Пользователь {} уже имеет активную заявку в статусе PENDING", userId);
            throw new IllegalStateException("Пользователь уже имеет активную заявку в статусе PENDING");
        }

        // Создаем новую заявку
        CreditRequest creditRequest = CreditRequest.builder()
                .userId(userId)
                .amount(requestDTO.getAmount())
                .term(requestDTO.getTerm())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .lastName(requestDTO.getLastName())
                .firstName(requestDTO.getFirstName())
                .middleName(requestDTO.getMiddleName())
                .documentType(requestDTO.getDocumentType())
                .documentSeries(requestDTO.getDocumentSeries())
                .documentNumber(requestDTO.getDocumentNumber())
                .documentIssuedBy(requestDTO.getDocumentIssuedBy())
                .documentIssuedDate(requestDTO.getDocumentIssuedDate())
                .build();

        creditRepository.save(creditRequest);
        log.info("Кредитная заявка сохранена: {}", creditRequest);

        requestDTO.setUserId(userId);
        // Отправляем данные в CamundaService
        camundaClient.startProcess(requestDTO);
        log.info("Процесс в CamundaService запущен для userId: {}", userId);

        return creditRequest;
    }

    public List<CreditRequest> getUserCredits(String userId) {
        log.info("Получение кредитных заявок для userId: {}", userId);
        return creditRepository.findByUserId(userId);
    }
}