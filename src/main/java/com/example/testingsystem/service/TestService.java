package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.model.SolutionMapper;
import com.example.testingsystem.repository.TestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final SolutionService solutionService;
    private final UserService userService;


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
