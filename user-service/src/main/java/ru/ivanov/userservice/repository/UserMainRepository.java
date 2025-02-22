package ru.ivanov.userservice.repository;

import ru.ivanov.userservice.entity.UserMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMainRepository extends JpaRepository<UserMain, Long> {
    Optional<UserMain> findByUsername(String username);
    boolean existsByUsername(String username);
}
