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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConfirmationTask implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(ConfirmationTask.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String CONFIRMATION_URL = "http://localhost:8084/api/confirmation/confirm";

    @Override
    public void execute(DelegateExecution execution) {
        log.info("ConfirmationTask запущен!");

        try {
            String userId = (String) execution.getVariable("userId");

            Object amountObj = execution.getVariable("amount");
            Object termObj = execution.getVariable("term");

            int amount = (amountObj instanceof BigDecimal)
                    ? ((BigDecimal) amountObj).intValueExact()
                    : (Integer) amountObj;

            int term = (termObj instanceof BigDecimal)
                    ? ((BigDecimal) termObj).intValueExact()
                    : (Integer) termObj;

            boolean fsspCheckPassed = (boolean) execution.getVariable("fsspCheckPassed");
            boolean kbCheckPassed = (boolean) execution.getVariable("kbCheckPassed");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("userId", userId);
            requestBody.put("amount", amount);
            requestBody.put("term", term);
            requestBody.put("fsspCheckPassed", fsspCheckPassed);
            requestBody.put("kbCheckPassed", kbCheckPassed);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            log.info("Отправка данных в ConfirmationService: {}", requestBody);
            ResponseEntity<String> response = restTemplate.exchange(CONFIRMATION_URL, HttpMethod.POST, requestEntity, String.class);
            log.info("Данные успешно отправлены в ConfirmationService. Ответ: {}", response.getBody());
        } catch (ArithmeticException e) {
            log.error("Ошибка приведения BigDecimal к Integer: {}", e.getMessage(), e);
            throw new RuntimeException("Неверный формат числа (ожидался целый): " + e.getMessage());
        } catch (HttpStatusCodeException e) {
            log.error("Ошибка HTTP-запроса: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Ошибка при отправке данных в ConfirmationService: {}", e.getMessage(), e);
        }
    }
}