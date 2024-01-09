package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;
import com.example.testingsystem.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService{

    private final TestRepository testRepository;

    @Override
    public Page<Test> getAllTests(Pageable pageable) {
        return testRepository.findAll(pageable);
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
    public Page<Test> getTestsByTitle(String title, Pageable pageable) {
        return testRepository.findTestsByTitleContaining(title, pageable);
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
