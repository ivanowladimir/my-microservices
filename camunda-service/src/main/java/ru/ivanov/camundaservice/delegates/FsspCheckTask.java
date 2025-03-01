package ru.ivanov.camundaservice.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FsspCheckTask implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(FsspCheckTask.class);
    private static final Random random = new Random();

    @Override
    public void execute(DelegateExecution execution) {
        boolean fsspCheckPassed = random.nextBoolean(); // Рандомный true/false
        execution.setVariable("fsspCheckPassed", fsspCheckPassed);
        log.info("Результат проверки ФССП: {}", fsspCheckPassed ? "Пройдено" : "Отклонено");
    }
}
