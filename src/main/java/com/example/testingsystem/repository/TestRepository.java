package com.example.testingsystem.repository;

import com.example.testingsystem.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
