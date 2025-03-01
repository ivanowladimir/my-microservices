package ru.ivanov.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_document", schema = "user_db")
public class UserDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType;  // Тип документа (паспорт, ВУ и т. д.)
    private String series;        // Серия
    private String number;        // Номер
    private String issuedBy;      // Кем выдано
    private String issuedDate;    // Дата выдачи
    private boolean isActive;     // Актуальность документа

    @OneToOne
    @JoinColumn(name = "user_main_id")
    private UserMain userMain;
}
