package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @MockBean
    QuestionRepository questionRepository;

    @Test
    public void getQuestionsList(){
        int id = 1;

        questionService.getQuestionsList(id);

        Mockito.verify(questionRepository).findQuestionsByTestId(id);
    }

    @Test
    public void saveQuestion(){
        Question question = new Question();

        questionRepository.save(question);

        Mockito.verify(questionRepository).save(question);
    }

}
