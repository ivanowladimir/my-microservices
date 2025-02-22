package ru.ivanov.creditservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRequestDTO {
    private BigDecimal amount;
    private int term;
}
