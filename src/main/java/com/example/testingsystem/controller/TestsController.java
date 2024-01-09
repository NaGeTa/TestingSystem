package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.service.TestServiceImpl;
import com.example.testingsystem.service.TestsControllerServiceImpl;
import com.example.testingsystem.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@Controller
@AllArgsConstructor
public class TestsController {

    private final TestsControllerServiceImpl testsControllerServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final TestServiceImpl testServiceImpl;

    @GetMapping("/tests")
    public String getTests(@RequestParam(required = false, value = "searchTitle") String searchTitle, Model model,
                           @RequestParam(defaultValue = "0") int page) {

        testsControllerServiceImpl.getTests(model, searchTitle, page);

        return "test/tests";
    }

    @GetMapping("/tests/{id}")
    public String getTestsCard(@PathVariable("id") int id, Model model) {

        testsControllerServiceImpl.getTestsCard(model, id);

        return "test/tests_card";
    }

    @PostMapping("/tests")
    public String finishTestsSolution(@ModelAttribute("answersList") AnswersList answersList, Model model) {

        model.addAttribute("solution", testsControllerServiceImpl.finishTestsSolution(answersList));

        return "test/tests_result";
    }

    @GetMapping("/statistic")
    public String getStatistic(Model model) {

        testsControllerServiceImpl.getStatistic(model);

        return "test/my_solutions";
    }

    @GetMapping("/newTest")
    public String createTest(Model model) {

        if(!userServiceImpl.hasAccess(Role.TEACHER_ROLE, Role.ADMIN_ROLE)){
            return "logic/error";
        }

        model.addAttribute("test", new Test());

        return "test/test_create";
    }

    @PostMapping("/newTest/questions")
    public String createQuestion(@ModelAttribute("test") @Valid Test test, BindingResult bindingResult, Model model) {

        if (testServiceImpl.countTestByTitle(test.getTitle()) > 0) {
            bindingResult.addError(new FieldError("test.getTitle()", "title",
                    "Тест с таким названием уже существует"));
        }

        if (bindingResult.hasErrors()) {
            return "test/test_create";
        }

        model.addAttribute("answersList", testsControllerServiceImpl.createQuestion(test));

        return "test/questions_create";
    }

    @PostMapping("/newTest")
    public String saveTest(@ModelAttribute("answersList") @Valid AnswersList answersList, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "test/questions_create";
        }

        testsControllerServiceImpl.saveTest(answersList);

        return "redirect:/tests";
    }

    @GetMapping("/myTests")
    public String getAllMyTests(Model model) {

        if (!userServiceImpl.hasAccess(Role.TEACHER_ROLE, Role.ADMIN_ROLE)) {
            return "logic/error";
        }

        testsControllerServiceImpl.getAllMyTests(model);

        return "test/my_tests";
    }

    @GetMapping("/myTests/{id}")
    public String getMyTestsSolutions(@PathVariable("id") int id, Model model) {

        testsControllerServiceImpl.getMyTestsSolutions(model, id);

        return "test/my_tests_solutions";
    }

    @PostMapping("/deleteTest/{id}")
    public String deleteTest(@PathVariable("id") int id){

        if (!userServiceImpl.hasAccess(Role.TEACHER_ROLE, Role.ADMIN_ROLE)) {
            return "logic/error";
        }

        testsControllerServiceImpl.deleteTest(id);

        return "redirect:/tests";
    }

    @ResponseBody
    @GetMapping("/saveResults/{id}")
    public ResponseEntity<ByteArrayResource> saveResults(@PathVariable int id){

        ByteArrayOutputStream outputStream = testsControllerServiceImpl.saveResults(id);

        return ResponseEntity
                .ok()
                .header("CONTENT-TYPE", MediaType.APPLICATION_PDF_VALUE)
                .body(new ByteArrayResource(outputStream.toByteArray()));
    }

}
