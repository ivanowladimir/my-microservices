package ru.ivanov.creditservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ivanov.creditservice.dto.CreditRequestDTO;

@FeignClient(name = "camunda-service", url = "http://localhost:8083/camunda")
public interface CamundaClient {
    @PostMapping("/start-process")
    void startProcess(@RequestBody CreditRequestDTO request);
}
