package ru.ivanov.camundaservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditRequestDTO {
    private String userId;
    private BigDecimal amount;
    private int term;
}
