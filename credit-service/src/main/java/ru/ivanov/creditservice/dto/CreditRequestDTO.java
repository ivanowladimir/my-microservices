package ru.ivanov.creditservice.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRequestDTO {

    private String userId;

    @Min(value = 1, message = "Сумма кредита должна быть больше 0")
    private BigDecimal amount;

    @Min(value = 1, message = "Срок кредита должен быть больше 0 месяцев")
    private int term;

    // Добавляем поля для ФИО и данных документа
    private String lastName; // Фамилия
    private String firstName; // Имя
    private String middleName; // Отчество
    private String documentType; // Тип документа
    private String documentSeries; // Серия документа
    private String documentNumber; // Номер документа
    private String documentIssuedBy; // Кем выдан документ
    private String documentIssuedDate; // Дата выдачи документа
}