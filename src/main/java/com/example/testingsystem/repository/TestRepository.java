package com.example.testingsystem.repository;

import com.example.testingsystem.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TestRepository extends JpaRepository<Test, Integer> {
    int countTestByTitle(String title);

    Page<Test> findTestsByTitleContaining(String title, Pageable pageable);

    List<Test> findAllByCreatorId(int id);

    int countTestByCreatorId(int id);
}
