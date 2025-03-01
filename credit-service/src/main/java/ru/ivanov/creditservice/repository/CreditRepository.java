package ru.ivanov.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivanov.creditservice.entity.CreditRequest;
import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<CreditRequest, Long> {
    List<CreditRequest> findByUserId(String userId);

    Optional<CreditRequest> findTopByUserIdAndStatusOrderByCreatedAtDesc(String userId, String status);
}