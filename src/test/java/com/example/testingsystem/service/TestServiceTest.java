package com.example.testingsystem.service;

import com.example.testingsystem.entity.Category;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.repository.TestRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestServiceTest {

    @Autowired
    TestService testService;

    @Mock
    TestRepository testRepository;

    @org.junit.jupiter.api.Test
    public void getAllTests() {
        testRepository.findAll();

        Mockito.verify(testRepository).findAll();
    }

    //интерфейсы
    @org.junit.jupiter.api.Test
    public void countTestByTitle() {
        String title = "title";

        testRepository.countTestByTitle(title);

        Mockito.verify(testRepository).countTestByTitle(title);
    }

    @org.junit.jupiter.api.Test
    public void saveTest() {
        Test test = new Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);

        testRepository.save(test);

        Mockito.verify(testRepository).save(test);
    }

    @org.junit.jupiter.api.Test
    public void getTestsByTitle() {
        String title = "title";

        testRepository.findTestsByTitleContaining(title);

        Mockito.verify(testRepository).findTestsByTitleContaining(title);
    }

    @org.junit.jupiter.api.Test
    public void getAllTestsByCreatorId() {
        int id = 1;

        testRepository.findAllByCreatorId(id);

        Mockito.verify(testRepository).findAllByCreatorId(id);
    }

    @org.junit.jupiter.api.Test
    public void getTestById() {
        int id = 1;

        testRepository.findById(id);

        Mockito.verify(testRepository).findById(id);
    }

    @org.junit.jupiter.api.Test
    public void getCountOfCreatedTests() {
        int id = 1;

        testRepository.countTestByCreatorId(id);

        Mockito.verify(testRepository).countTestByCreatorId(id);
    }

}
