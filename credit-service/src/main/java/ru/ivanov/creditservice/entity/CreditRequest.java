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

    private String userId;      // ID пользователя из User Service
    private BigDecimal amount;  // Сумма кредита
    private int term;           // Срок кредита в месяцах
    private String status;      // "PENDING", "APPROVED", "REJECTED"

    private LocalDateTime createdAt;
}