package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;
import com.example.testingsystem.repository.TestRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class TestServiceTest {

    @Mock
    TestRepository testRepository;

    @org.junit.jupiter.api.Test
    public void getAllTests() {
        testRepository.findAll();

        Mockito.verify(testRepository).findAll();
    }

    //интерфейсы
    @org.junit.jupiter.api.Test
    public void countTestByTitle(String title) {
        testRepository.countTestByTitle(title);

        Mockito.verify(testRepository).countTestByTitle(title);
    }

    @org.junit.jupiter.api.Test
    public void saveTest(Test test) {
        testRepository.save(test);

        Mockito.verify(testRepository).save(test);
    }

    @org.junit.jupiter.api.Test
    public void getTestsByTitle(String title) {
        testRepository.findTestsByTitleContaining(title);

        Mockito.verify(testRepository).findTestsByTitleContaining(title);
    }

    public void getAllTestsByCreatorId(int id) {
        testRepository.findAllByCreatorId(id);

        Mockito.verify(testRepository).findAllByCreatorId(id);
    }

    public void getTestById(int id) {
        testRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        Mockito.verify(testRepository).findById(id);
    }

    public void getCountOfCreatedTests(int id) {
        testRepository.countTestByCreatorId(id);

        Mockito.verify(testRepository).countTestByCreatorId(id);
    }

}
