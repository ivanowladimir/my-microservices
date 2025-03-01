package ru.ivanov.creditservice.kafka;

import lombok.Data;

@Data
public class CreditConfirmationMessage {
    private String userId;
    private String status;
}