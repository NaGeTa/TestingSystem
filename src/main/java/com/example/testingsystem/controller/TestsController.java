package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Question;
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

import java.util.ArrayList;
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

        solution.rating();
        solutionService.saveSolution(solution);
        solution.getTest().setCountOfSolutions(solution.getTest().getCountOfSolutions()+1);

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
    public String createTest(Model model){
        model.addAttribute("test", new Test());

        return "test/test_create";
    }

    @PostMapping("/newTest/questions")
    public String createQuestion(@ModelAttribute("test") Test test, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        test.setCreator(userService.getUserByLogin(login));

        AnswersList answersList = new AnswersList(new ArrayList<>());
        int count = test.getCountOfQuestions();
        for(int i = 0; i<count; i++){
            answersList.getAnswers().add(new Question());
            answersList.getAnswers().get(i).setTest(test);
            answersList.getAnswers().get(i).setNumOfQuestion(i + 1);
        }

        model.addAttribute("answersList", answersList);

        return "test/questions_create";
    }

    @PostMapping("/newTest")
    public String saveTest(@ModelAttribute("answersList") AnswersList answersList){


        Test test = answersList.getAnswers().get(0).getTest();

        testService.saveTest(test);

        answersList.getAnswers().forEach(question -> {
//            testService.saveTest(question.getTest());
            question.setTest(test);
            questionService.saveQuestion(question);
        });

        return "redirect:/tests";
    }
}
