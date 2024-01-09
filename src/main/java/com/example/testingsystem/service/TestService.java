package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {
    Page<Test> getAllTests(Pageable pageable);
    int countTestByTitle(String title);
    void saveTest(Test test);
    Page<Test> getTestsByTitle(String title, Pageable pageable);
    List<Test> getAllTestsByCreatorId(int id);
    Test getTestById(int id);
    int getCountOfCreatedTests(int id);
    void deleteTestById(int id);
}
