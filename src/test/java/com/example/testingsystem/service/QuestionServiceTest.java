package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    QuestionRepository questionRepository;

    QuestionService questionService;

    @Test
    public void getQuestionsList(){
        questionRepository.findQuestionsByTestId(1);

        Mockito.verify(questionRepository).findQuestionsByTestId(1);
    }

    @Test
    public void saveQuestion(){
        Question question = new Question();

        questionRepository.save(question);

        Mockito.verify(questionRepository).save(question);
    }

}
