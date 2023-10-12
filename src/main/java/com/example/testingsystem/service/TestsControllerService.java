package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.model.AnswersList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestsControllerService {

    private final UserService userService;
    private final SolutionService solutionService;
    private final SendService sendService;
    private final TestService testService;
    private final QuestionService questionService;

    public Solution finishTestsSolution(AnswersList answersList) {
        Solution solution = new Solution();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        solution.setUser(userService.getUserByLogin(login));
        solution.setTest(answersList.getAnswers().get(0).getTest());

        answersList.getAnswers().forEach((question) -> {
            if (question.isRight()) {
                solution.setCountOfRightAnswers(solution.getCountOfRightAnswers() + 1);
            }
            solution.setCountOfQuestions(solution.getCountOfQuestions() + 1);
        });

        solution.rating();
        solution.getTest().setCountOfSolutions(solution.getTest().getCountOfSolutions() + 1);
        solutionService.saveSolution(solution);

        if (solution.getTest().getCreator().isDoSend()) {
            sendService.send(solution);
        }

        return solution;
    }

    public AnswersList createQuestion(Test test) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        test.setCreator(userService.getUserByLogin(login));

        AnswersList answersList = new AnswersList(new ArrayList<>());
        int count = test.getCountOfQuestions();
        for (int i = 0; i < count; i++) {
            answersList.getAnswers().add(new Question());
            answersList.getAnswers().get(i).setTest(test);
            answersList.getAnswers().get(i).setNumOfQuestion(i + 1);
        }
        return answersList;
    }

    public ByteArrayOutputStream saveResults(int id){

        List<Solution> list = solutionService.getSolutionsByTestId(id);

        if(list.isEmpty()) {
            return null;
        }

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.open();

        BaseFont baseFont;
        String path = "static/font/font.ttf";
        try {
            baseFont = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
        Font font = new Font(baseFont, 16);

        Paragraph paragraph;

        paragraph = new Paragraph(list.get(0).getTest().getTitle() +
                "\n  Категория: " + list.get(0).getTest().getCategory().value +
                "\n  Кол-во вопросов: " + list.get(0).getTest().getCountOfQuestions() +
                "\n  Кол-во решений: " + list.get(0).getTest().getCountOfSolutions() +
                "\n  Дата создания: " + list.get(0).getTest().getDateOfCreation() + "\n" +
                "\nРешения:\n\n", font);

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        font = new Font(baseFont, 13);


        for (Solution solution : list) {
            paragraph = new Paragraph("\nСтудент: " + solution.getUser().getFirst_name()  + " " + solution.getUser().getLast_name()
                    + "\n Оценка: " + solution.getMark().value
                    + "\n Дата: " + solution.getDateOfSolution()
                    + "\n Количество правильных ответов: " + solution.getCountOfRightAnswers()
                    + "\n_________________________________________", font);
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }

        document.close();

        return outputStream;
    }

    public void getTests(Model model, String searchTitle){
        if ((searchTitle == null) || (searchTitle.equals(""))) {
            model.addAttribute("tests", testService.getAllTests());
        } else {
            model.addAttribute("tests", testService.getTestsByTitle(searchTitle));
        }
    }

    public void getStatistic(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        int id = userService.getUserByLogin(login).getId();

        List<Solution> solutions = solutionService.getSolutionsByUserId(id);

        model.addAttribute("solutions", solutions);
    }

    public void saveTest(AnswersList answersList){
        Test test = answersList.getAnswers().get(0).getTest();

        testService.saveTest(test);

        answersList.getAnswers().forEach(question -> {
            question.setTest(test);
            questionService.saveQuestion(question);
        });
    }

    public void getAllMyTests(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userService.getUserByLogin(login);

        model.addAttribute("tests", testService.getAllTestsByCreatorId(user.getId()));
    }

    public void getTestsCard(Model model, int id){
        AnswersList answersList = new AnswersList(questionService.getQuestionsList(id));

        model.addAttribute("answersList", answersList);
    }

    public void getMyTest(Model model, int id){
        Test test = testService.getTestById(id);
        List<Solution> solutions = solutionService.getSolutionsByTestId(test.getId());

        model.addAttribute("test", test);
        model.addAttribute("solutions", solutions);
    }
}
