package ru.ivanov.creditservice.client;

import ru.ivanov.creditservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/users", fallback = UserServiceClient.Fallback.class)
public interface UserServiceClient {

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUserById")
    @GetMapping("/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);

    @Component
    class Fallback implements UserServiceClient {
        @Override
        public UserDTO getUserById(Long userId) {
            UserDTO fallbackUser = new UserDTO();
            fallbackUser.setUsername("Unknown");
            fallbackUser.setEmail("unknown@example.com");
            return fallbackUser;
        }
    }
}