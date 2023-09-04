package com.example.testingsystem.repository;

import com.example.testingsystem.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countUsersByLogin(String value);

    int countUsersByEmail(String value);

    Optional<User> findByLogin(@NotBlank(message = "Message from repository") String login);
}
