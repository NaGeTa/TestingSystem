package com.example.testingsystem.controller;

import com.example.testingsystem.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TestsController {

    TestService testService;

    @GetMapping("/tests")
    public String getTests(Model model){

        model.addAttribute("tests", testService.getAllTests());

        return "tests/tests";
    }
}
