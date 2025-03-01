package ru.ivanov.camundaservice.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.camundaservice.dto.CreditRequestDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/camunda")
public class CreditProcessController {
    private static final Logger log = LoggerFactory.getLogger(CreditProcessController.class);
    private final RuntimeService runtimeService;

    public CreditProcessController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping("/start-process")
    public ResponseEntity<String> startProcess(@RequestBody CreditRequestDTO request) {
        log.info("Получен запрос на запуск процесса с данными: {}", request);

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", request.getUserId());
        variables.put("amount", request.getAmount());
        variables.put("term", request.getTerm());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("credit_process", variables);
        log.info("Процесс запущен: ID = {}", processInstance.getId());

        return ResponseEntity.ok("Процесс запущен: " + processInstance.getId());
    }
}