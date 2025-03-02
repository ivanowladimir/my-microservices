package ru.ivanov.creditservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // ID пользователя из User Service
    private BigDecimal amount; // Сумма кредита
    private int term; // Срок кредита в месяцах
    private String status; // "PENDING", "APPROVED", "REJECTED"

    private LocalDateTime createdAt;

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