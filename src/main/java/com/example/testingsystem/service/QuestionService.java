package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestionsList(int id){
        return questionRepository.findQuestionsByTestId(id);
    }

    public void saveQuestion(Question question){
        questionRepository.save(question);
    }
}
