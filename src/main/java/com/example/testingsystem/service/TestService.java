package com.example.testingsystem.service;

import com.example.testingsystem.entity.Test;
import com.example.testingsystem.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TestService {

    TestRepository testRepository;

    public List<Test> getAllTests(){
        return testRepository.findAll();
    }
}
