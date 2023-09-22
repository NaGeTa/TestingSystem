package com.example.testingsystem.repository;

import com.example.testingsystem.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Integer> {
    int countTestByTitle(String title);

    List<Test> findAllByTitle(String title);
}
