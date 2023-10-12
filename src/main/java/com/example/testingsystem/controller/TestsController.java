package com.example.testingsystem.controller;

import com.example.testingsystem.entity.*;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.mapper.SolutionMapper;
import com.example.testingsystem.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@AllArgsConstructor
public class TestsController {

    private final TestsControllerService service;
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final SolutionService solutionService;
    private final SolutionMapper solutionMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @GetMapping("/tests")
    public String getTests(@RequestParam(required = false, value = "searchTitle") String searchTitle, Model model) {

        if ((searchTitle == null) || (searchTitle.equals(""))) {
            model.addAttribute("tests", testService.getAllTests());
        } else {
            model.addAttribute("tests", testService.getTestsByTitle(searchTitle));
        }

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

        model.addAttribute("solution", service.finishTestsSolution(answersList));

        return "test/tests_result";
    }

    @GetMapping("/statistic")
    public String getStatistic(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        int id = userService.getUserByLogin(login).getId();

        List<Solution> solutions = solutionService.getSolutionsByUserId(id);

        model.addAttribute("solutions", solutions);

        return "test/my_solutions";
    }

    @GetMapping("/newTest")
    public String createTest(Model model) {

        if(!userService.hasAccess(Role.TEACHER_ROLE, Role.ADMIN_ROLE)){
            return "logic/error";
        }

        model.addAttribute("test", new Test());
        return "test/test_create";
    }

    @PostMapping("/newTest/questions")
    public String createQuestion(@ModelAttribute("test") @Valid Test test, BindingResult bindingResult, Model model) {

        if (testService.countTestByTitle(test.getTitle()) > 0) {
            bindingResult.addError(new FieldError("test.getTitle()", "title",
                    "Тест с таким названием уже существует"));
        }

        if (bindingResult.hasErrors()) {
            return "test/test_create";
        }

        model.addAttribute("answersList", service.createQuestion(test));

        return "test/questions_create";
    }

    @PostMapping("/newTest")
    public String saveTest(@ModelAttribute("answersList") @Valid AnswersList answersList,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "test/questions_create";
        }

        Test test = answersList.getAnswers().get(0).getTest();

        testService.saveTest(test);

        answersList.getAnswers().forEach(question -> {
            question.setTest(test);
            questionService.saveQuestion(question);
        });

        return "redirect:/tests";
    }

    @GetMapping("/myTests")
    public String getAllMyTests(Model model) {

        if (!userService.hasAccess(Role.TEACHER_ROLE, Role.ADMIN_ROLE)) {
            return "logic/error";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userService.getUserByLogin(login);

        model.addAttribute("tests", testService.getAllTestsByCreatorId(user.getId()));

        return "test/my_tests";
    }

    @GetMapping("/myTests/{id}")
    public String getMyTest(@PathVariable("id") int id, Model model) {

        Test test = testService.getTestById(id);
        List<Solution> solutions = solutionService.getSolutionsByTestId(test.getId());

        model.addAttribute("test", test);
        model.addAttribute("solutions", solutions);

        return "test/my_tests_solutions";
    }

    @ResponseBody
    @GetMapping("/saveResults/{id}")
    public ResponseEntity<ByteArrayResource> saveResults(@PathVariable int id){

        ByteArrayOutputStream outputStream = service.saveResults(id);

        return ResponseEntity
                .ok()
                .header("CONTENT-TYPE", MediaType.APPLICATION_PDF_VALUE)
                .body(new ByteArrayResource(outputStream.toByteArray()));
    }

}
