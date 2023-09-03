package com.example.testingsystem.repository;

import com.example.testingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countUsersByLogin(String value);

    int countUsersByEmail(String value);

    //int countUsersByLoginOrEmail(String value);
}
