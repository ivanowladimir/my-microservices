package ru.ivanov.camundaservice.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KbCheckTask implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(KbCheckTask.class);
    private static final Random random = new Random();

    @Override
    public void execute(DelegateExecution execution) {
        boolean kbCheckPassed = random.nextBoolean(); // Рандомный true/false
        execution.setVariable("kbCheckPassed", kbCheckPassed);
        log.info("Результат проверки КБ: {}", kbCheckPassed ? "Пройдено" : "Отклонено");
    }
}