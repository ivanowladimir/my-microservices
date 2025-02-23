package ru.ivanov.creditservice.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRequestDTO {

    @Min(value = 1, message = "Сумма кредита должна быть больше 0")
    private BigDecimal amount;

    @Min(value = 1, message = "Срок кредита должен быть больше 0 месяцев")
    private int term;
}
