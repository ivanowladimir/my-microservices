package ru.ivanov.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // Исключает null-поля из JSON
public class UserDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // Личные данные
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String birthPlace;

    // Контактные данные
    private String phone;
    private boolean contactIsActive;

    // Адрес
    private String country;
    private String city;
    private String street;
    private String house;
    private boolean addressIsActive;

    // Документ
    private String documentType;
    private String series;
    private String number;
    private String issuedBy;
    private String issuedDate;
    private boolean documentIsActive;
}