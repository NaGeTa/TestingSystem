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
public class TestServiceImpl implements TestService{

    private final TestRepository testRepository;

    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public int countTestByTitle(String title) {
        return testRepository.countTestByTitle(title);
    }

    @Override
    public void saveTest(Test test) {
        testRepository.save(test);
    }

    @Override
    public List<Test> getTestsByTitle(String title) {
        return testRepository.findTestsByTitleContaining(title);
    }

    @Override
    public List<Test> getAllTestsByCreatorId(int id) {
        return testRepository.findAllByCreatorId(id);
    }

    @Override
    public Test getTestById(int id) {
        return testRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    @Override
    public int getCountOfCreatedTests(int id) {
        return testRepository.countTestByCreatorId(id);
    }

    @Override
    public void deleteTestById(int id) {
        testRepository.delete(getTestById(id));
    }


}
