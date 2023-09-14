package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
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

    public List<Test> getAllTests(){
        return testRepository.findAll();
    }
//интерфейсы
    public Test getTest(int id){
        return testRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }
}
