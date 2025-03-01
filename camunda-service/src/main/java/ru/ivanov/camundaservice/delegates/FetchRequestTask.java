package ru.ivanov.camundaservice.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FetchRequestTask implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(FetchRequestTask.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String CREDIT_SERVICE_URL = "http://credit-service:8082/credit/user";
    private static final String AUTH_TOKEN = "dGhpcy1pcy1hLXNlY3JldC1rZXktZm9yLWp3dC1hdXRoZW50aWNhdGlvbi0xMjM0NTY3OAo=";

    @Override
    public void execute(DelegateExecution execution) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", AUTH_TOKEN);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            log.info("Запрос кредитных заявок из CreditService...");
            ResponseEntity<String> response = restTemplate.exchange(
                    CREDIT_SERVICE_URL, HttpMethod.GET, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                log.info("Получен ответ: {}", responseBody);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(responseBody);

                if (jsonResponse.isArray() && jsonResponse.size() > 0) {
                    JsonNode latestRequest = jsonResponse.get(0); // Берем первую заявку

                    String userId = latestRequest.get("userId").asText();
                    int amount = latestRequest.get("amount").asInt();
                    int term = latestRequest.get("term").asInt();

                    execution.setVariable("userId", userId);
                    execution.setVariable("amount", amount);
                    execution.setVariable("term", term);

                    log.info("Загружена заявка: userId={}, amount={}, term={}", userId, amount, term);
                } else {
                    log.warn("Нет активных кредитных заявок.");
                }
            } else {
                log.error("Ошибка при запросе кредитных заявок. Код: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Ошибка при загрузке данных из CreditService: {}", e.getMessage(), e);
        }
    }
}