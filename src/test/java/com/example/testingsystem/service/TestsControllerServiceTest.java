package com.example.testingsystem.service;

import com.example.testingsystem.entity.*;
import com.example.testingsystem.model.AnswersList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestsControllerServiceTest {
    @MockBean
    UserService userService;
    @MockBean
    SolutionService solutionService;
    @MockBean
    SendService sendService;
    @MockBean
    TestService testService;
    @MockBean
    QuestionService questionService;
    @MockBean
    SecurityContextHolder securityContextHolder;

    @Autowired
    TestsControllerService testsControllerService;

//    @Test
//    public void finishTestsSolution() {
//
//        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
//        List<Question> list = List.of(new Question(0, null, 0, 1, null, null, null, null, test, 1));
//        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
//        Solution solution = new Solution(0, user, test, new Date(), 1, 1, Mark.A);
//
//        Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn()
//
//        Solution solution1 = testsControllerService.finishTestsSolution(new AnswersList(list));
//
//        Assertions.assertEquals(solution, solution1);
//
//    }
//
//    public AnswersList createQuestion(Test test) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String login = authentication.getName();
//
//        test.setCreator(userService.getUserByLogin(login));
//
//        AnswersList answersList = new AnswersList(new ArrayList<>());
//        int count = test.getCountOfQuestions();
//        for (int i = 0; i < count; i++) {
//            answersList.getAnswers().add(new Question());
//            answersList.getAnswers().get(i).setTest(test);
//            answersList.getAnswers().get(i).setNumOfQuestion(i + 1);
//        }
//
//        return answersList;
//    }
//
    @Test
    public void saveResults(){
        int id = 1;
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        List<Solution> list = List.of(new Solution(1, user, test, new Date(), 0, 0, Mark.A));

        Mockito.when(solutionService.getSolutionsByTestId(id)).thenReturn(list);

        testsControllerService.saveResults(id);

    }
//
//    public void getTests(Model model, String searchTitle){
//        if ((searchTitle == null) || (searchTitle.equals(""))) {
//            model.addAttribute("tests", testService.getAllTests());
//        } else {
//            model.addAttribute("tests", testService.getTestsByTitle(searchTitle));
//        }
//
//    }
//
//    public void getStatistic(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String login = authentication.getName();
//        int id = userService.getUserByLogin(login).getId();
//
//        List<Solution> solutions = solutionService.getSolutionsByUserId(id);
//
//        model.addAttribute("solutions", solutions);
//
//    }
//
//    public void saveTest(AnswersList answersList){
//        Test test = answersList.getAnswers().get(0).getTest();
//
//        testService.saveTest(test);
//
//        answersList.getAnswers().forEach(question -> {
//            question.setTest(test);
//            questionService.saveQuestion(question);
//        });
//
//    }
//
//    public void getAllMyTests(Model model){
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String login = authentication.getName();
//        User user = userService.getUserByLogin(login);
//
//        model.addAttribute("tests", testService.getAllTestsByCreatorId(user.getId()));
//
//    }
//
//    public void getTestsCard(Model model, int id){
//        AnswersList answersList = new AnswersList(questionService.getQuestionsList(id));
//
//        model.addAttribute("answersList", answersList);
//
//    }
//
//    public void getMyTestsSolutions(Model model, int id){
//        Test test = testService.getTestById(id);
//        List<Solution> solutions = solutionService.getSolutionsByTestId(test.getId());
//
//        model.addAttribute("test", test);
//        model.addAttribute("solutions", solutions);
//
//    }
}
