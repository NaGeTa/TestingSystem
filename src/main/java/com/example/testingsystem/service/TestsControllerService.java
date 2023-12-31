package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.model.AnswersList;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;

public interface TestsControllerService {
    Solution finishTestsSolution(AnswersList answersList);
    AnswersList createQuestion(Test test);
    ByteArrayOutputStream saveResults(int id);
    void getTests(Model model, String searchTitle);
    void getStatistic(Model model);
    void saveTest(AnswersList answersList);
    void getAllMyTests(Model model);
    void getTestsCard(Model model, int id);
    void getMyTestsSolutions(Model model, int id);
    void deleteTest(int id);
}
