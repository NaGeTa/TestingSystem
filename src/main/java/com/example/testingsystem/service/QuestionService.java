package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsList(int id);
    void saveQuestion(Question question);
}
