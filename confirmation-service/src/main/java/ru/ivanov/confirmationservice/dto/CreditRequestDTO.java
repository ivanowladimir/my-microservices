package ru.ivanov.confirmationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDTO {
    private String userId;
    private BigDecimal amount;
    private int term;
    private boolean fsspCheckPassed;
    private boolean kbCheckPassed;
}