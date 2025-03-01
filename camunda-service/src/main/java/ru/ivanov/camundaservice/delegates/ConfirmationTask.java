package ru.ivanov.camundaservice.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConfirmationTask implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(ConfirmationTask.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String CONFIRMATION_URL = "http://confirmation-service/api/confirm";

    @Override
    public void execute(DelegateExecution execution) {
        String userId = (String) execution.getVariable("userId");
        String amount = execution.getVariable("amount").toString();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userId", userId);
        requestBody.put("amount", amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            log.info("Отправка запроса в ConfirmationService: {}", requestBody);
            ResponseEntity<String> response = restTemplate.exchange(
                    CONFIRMATION_URL, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                execution.setVariable("confirmationStatus", "CONFIRMATION_SENT");
                log.info("Запрос успешно отправлен. Ответ: {}", response.getBody());
            } else {
                execution.setVariable("confirmationStatus", "CONFIRMATION_FAILED");
                log.warn("Ошибка отправки заявки, статус: {}", response.getStatusCode());
            }
        } catch (HttpStatusCodeException e) {
            execution.setVariable("confirmationStatus", "CONFIRMATION_FAILED");
            log.error("Ошибка HTTP-запроса: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            execution.setVariable("confirmationStatus", "CONFIRMATION_FAILED");
            log.error("Ошибка при отправке заявки: {}", e.getMessage(), e);
        }
    }
}