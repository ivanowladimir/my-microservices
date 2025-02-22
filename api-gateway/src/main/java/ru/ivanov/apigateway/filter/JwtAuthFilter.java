package ru.ivanov.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.ivanov.apigateway.util.JwtUtils;

import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();

        log.info("API Gateway: Запрос на {}", path);

        // Пропускаем логин без проверки токена
        if (path.equals("/api/auth/login") || path.equals("/auth/login")) {
            log.info("API Gateway: Запрос на login, пропускаем без проверки токена.");
            return chain.filter(exchange);
        }

        String token = extractToken(request);
        if (token == null || !jwtUtils.validateToken(token)) {
            log.error("API Gateway: Токен не валиден или отсутствует!");
            return unauthorizedResponse(exchange);
        }

        String username = jwtUtils.extractUsername(token);
        log.info("API Gateway: Токен валиден, пользователь: {}", username);

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("Authorization", "Bearer " + token)
                .header("X-User-Id", username)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");

        if (headers == null || headers.isEmpty()) {
            log.warn("API Gateway: Нет заголовка Authorization!");
            return null;
        }

        String header = headers.get(0);
        log.debug("API Gateway: Получен Authorization: {}", header);

        if (header.startsWith("Bearer ")) {
            String token = header.substring(7);
            log.debug("API Gateway: Извлечённый токен: {}", token);
            return token;
        }

        log.warn("API Gateway: Токен не начинается с 'Bearer '!", header);
        return null;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.warn("API Gateway: Запрос отклонён - неавторизован!");
        return response.setComplete();
    }
}