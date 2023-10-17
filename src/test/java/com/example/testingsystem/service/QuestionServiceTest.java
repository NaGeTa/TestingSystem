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

    @Test
    public void getQuestionsList(int id){
        questionRepository.findQuestionsByTestId(id);

        Mockito.verify(questionRepository).findQuestionsByTestId(id);
    }

    public void saveQuestion(Question question){
        questionRepository.save(question);

        Mockito.verify(questionRepository).save(question);
    }

}
