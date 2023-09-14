package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.service.QuestionService;
import com.example.testingsystem.service.TestService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@AllArgsConstructor
public class TestsController {

    private final TestService testService;
    private final QuestionService questionService;

    @GetMapping("/tests")
    public String getTests(Model model) {

        model.addAttribute("tests", testService.getAllTests());

        return "test/tests";
    }

    @GetMapping("/tests/{id}")
    public String getTestsCard(@PathVariable("id") int id, Model model) {

        AnswersList answersList = new AnswersList(questionService.getQuestionsList(id));

        model.addAttribute("answersList", answersList);

        return "test/ttest";
    }

    @PostMapping("/tests")
    public String finishTestsSolution(@ModelAttribute("answersList") AnswersList answersList) {


        answersList.getAnswers().stream().map(question -> question.getChoiceAnswer())
                .forEach((answer) -> System.out.println(answer));

        return "test/test_result";
    }
}
