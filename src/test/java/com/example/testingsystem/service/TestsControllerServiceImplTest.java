package com.example.testingsystem.service;

import com.example.testingsystem.entity.*;
import com.example.testingsystem.model.AnswersList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestsControllerServiceImplTest {

    @Autowired
    TestsControllerServiceImpl testsControllerServiceImpl;

    @MockBean
    UserServiceImpl userServiceImpl;
    @MockBean
    SolutionServiceImpl solutionServiceImpl;
    @MockBean
    SendServiceImpl sendServiceImpl;
    @MockBean
    TestServiceImpl testServiceImpl;
    @MockBean
    QuestionServiceImpl questionServiceImpl;
    @MockBean
    Authentication authentication;
    @MockBean
    Model model;

    @Test
    public void finishTestsSolution() {
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), user, Category.BIOLOGY);
        List<Question> list = List.of(new Question(0, null, 0, 1, null, null, null, null, test, 1));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Solution solution = testsControllerServiceImpl.finishTestsSolution(new AnswersList(list));
        Solution solutionTest = new Solution(0, null, test, solution.getDateOfSolution(), 1, 1, Mark.A);

        Assertions.assertEquals(solutionTest, solution);
    }

    @Test
    public void createQuestion() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), user, Category.BIOLOGY);
        String login = "nickname";
        Mockito.when(authentication.getName()).thenReturn(login);
        Mockito.when(userServiceImpl.getUserByLogin(login)).thenReturn(user);

        AnswersList answersListTest = new AnswersList(new ArrayList<>());
        AnswersList answersList = testsControllerServiceImpl.createQuestion(test);

        Assertions.assertEquals(answersListTest, answersList);
    }

    @Test
    public void saveResults(){
        int id = 1;
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        List<Solution> list = List.of(new Solution(1, user, test, new Date(), 0, 0, Mark.A));

        Mockito.when(solutionServiceImpl.getSolutionsByTestId(id)).thenReturn(list);

        testsControllerServiceImpl.saveResults(id);

    }

    @Test
    public void getTests(){
        String searchTitle = "title";
        testsControllerServiceImpl.getTests(model, searchTitle, 0);

        Mockito.verify(model).addAttribute("tests", testServiceImpl.getTestsByTitle(searchTitle,
                PageRequest.of(0, 6)));
    }

    @Test
    public void getStatistic(){
        SecurityContextHolder.getContext().setAuthentication(authentication);
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        List<Solution> list = List.of(new Solution(1, user, test, new Date(), 0, 0, Mark.A));
        String login = "nickname";

        Mockito.when(authentication.getName()).thenReturn(login);
        Mockito.when(userServiceImpl.getUserByLogin(login)).thenReturn(user);
        Mockito.when(solutionServiceImpl.getSolutionsByUserId(user.getId())).thenReturn(list);

        testsControllerServiceImpl.getStatistic(model);
        Mockito.verify(model).addAttribute("solutions", list);

    }

    @Test
    public void saveTest(){
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
        AnswersList answersList = new AnswersList(List.of(new Question(1, "", 1, 1, "", "", "", "", test, 1)));

        testsControllerServiceImpl.saveTest(answersList);

        Mockito.verify(testServiceImpl).saveTest(test);
        Mockito.verify(questionServiceImpl).saveQuestion(answersList.getAnswers().get(0));

    }

    @Test
    public void getAllMyTests(){
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        String login = "nickname";

        Mockito.when(authentication.getName()).thenReturn(login);
        Mockito.when(userServiceImpl.getUserByLogin(login)).thenReturn(user);

        testsControllerServiceImpl.getAllMyTests(model);

        Mockito.verify(model).addAttribute("tests", testServiceImpl.getAllTestsByCreatorId(user.getId()));

    }

    @Test
    public void getTestsCard(){
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), user, Category.BIOLOGY);
        List<Question> list = List.of(new Question(0, null, 0, 1, null, null, null, null, test, 1));
        int id = 1;

        Mockito.when(questionServiceImpl.getQuestionsList(id)).thenReturn(list);

        testsControllerServiceImpl.getTestsCard(model, id);

        Mockito.verify(model).addAttribute("answersList", new AnswersList(list));

    }

    @Test
    public void getMyTestsSolutions(){
        int id = 1;
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), user, Category.BIOLOGY);
        List<Solution> list = List.of(new Solution(1, user, test, new Date(), 0, 0, Mark.A));

        Mockito.when(testServiceImpl.getTestById(id)).thenReturn(test);
        Mockito.when(solutionServiceImpl.getSolutionsByTestId(id)).thenReturn(list);

        testsControllerServiceImpl.getMyTestsSolutions(model, id);

        Mockito.verify(model).addAttribute("test", test);
        Mockito.verify(model).addAttribute("solutions", list);
    }
}
