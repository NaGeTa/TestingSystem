package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;
import com.example.testingsystem.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<Test> getAllTests(){
        return testRepository.findAll();
    }
//интерфейсы
    public int countTestByTitle(String title){
        return testRepository.countTestByTitle(title);
    }

    public void saveTest(Test test){
        testRepository.save(test);
    }

    public List<Test> getTestByTitle(String title){
        return testRepository.findAllByTitle(title);
    }
}
