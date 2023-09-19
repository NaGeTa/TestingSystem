package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.service.QuestionService;
import com.example.testingsystem.service.SolutionService;
import com.example.testingsystem.service.TestService;
import com.example.testingsystem.service.UserService;
import lombok.AllArgsConstructor;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class TestsController {

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final SolutionService solutionService;

    @GetMapping("/tests")
    public String getTests(Model model) {

        model.addAttribute("tests", testService.getAllTests());

        return "test/tests";
    }

    @GetMapping("/tests/{id}")
    public String getTestsCard(@PathVariable("id") int id, Model model) {

        AnswersList answersList = new AnswersList(questionService.getQuestionsList(id));

        model.addAttribute("answersList", answersList);

        return "test/tests_card";
    }

    @PostMapping("/tests")
    public String finishTestsSolution(@ModelAttribute("answersList") AnswersList answersList, Model model) {

        Solution solution = new Solution();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        solution.setUser(userService.getUserByLogin(login));
        solution.setTest(answersList.getAnswers().get(0).getTest());

        answersList.getAnswers().forEach((question) -> {
            if(question.isRight()){
                solution.setCountOfRightAnswers(solution.getCountOfRightAnswers()+1);
            }
            solution.setCountOfQuestions(solution.getCountOfQuestions()+1);
        });


//        for (Question question: answersList.getAnswers()) {
//            if(question.isRight()){
//                solution.setCountOfRightAnswers(solution.getCountOfRightAnswers()+1);
//            }
//            solution.setCountOfQuestions(solution.getCountOfQuestions()+1);
//        }

        solution.rating();

        solutionService.saveSolution(solution);

        model.addAttribute("solution", solution);

        return "test/tests_result";
    }

    @GetMapping("/statistic")
    public String getStatistic(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        int id = userService.getUserByLogin(login).getId();

        List<Solution> solutions = solutionService.getSolutionsByUserId(id);

        model.addAttribute("solutions", solutions);

        return "test/my_solutions";
    }

    @GetMapping("/newTest")
    public String createNewTest(Model model){
        model.addAttribute("test", new Test());

        return "test/test_create";
    }
}
