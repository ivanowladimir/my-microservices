package ru.ivanov.confirmationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDTO {
    private String userId;
    private boolean fsspCheckPassed;
    private boolean kbCheckPassed;
}