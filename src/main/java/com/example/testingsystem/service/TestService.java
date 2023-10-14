package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;
import com.example.testingsystem.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TestService {

    private final TestRepository testRepository;


    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    //интерфейсы
    public int countTestByTitle(String title) {
        return testRepository.countTestByTitle(title);
    }

    public void saveTest(Test test) {
        testRepository.save(test);
    }

    public List<Test> getTestsByTitle(String title) {
        return testRepository.findTestsByTitleContaining(title);
    }

    public List<Test> getAllTestsByCreatorId(int id) {
        return testRepository.findAllByCreatorId(id);
    }

    public Test getTestById(int id) {
        return testRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    public int getCountOfCreatedTests(int id) {
        return testRepository.countTestByCreatorId(id);
    }

}
