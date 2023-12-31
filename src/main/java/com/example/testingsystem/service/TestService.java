package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;

import java.util.List;

public interface TestService {
    List<Test> getAllTests();
    int countTestByTitle(String title);
    void saveTest(Test test);
    List<Test> getTestsByTitle(String title);
    List<Test> getAllTestsByCreatorId(int id);
    Test getTestById(int id);
    int getCountOfCreatedTests(int id);
    void deleteTestById(int id);
}
