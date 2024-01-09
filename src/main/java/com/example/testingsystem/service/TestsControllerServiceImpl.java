package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.repository.TestRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
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
public class TestsControllerServiceImpl implements TestsControllerService{

    private final UserServiceImpl userServiceImpl;
    private final SolutionServiceImpl solutionServiceImpl;
    private final SendServiceImpl sendServiceImpl;
    private final TestServiceImpl testServiceImpl;
    private final QuestionServiceImpl questionServiceImpl;
    TestRepository testRepository;
    private static final Logger logger = Logger.getLogger(TestsControllerServiceImpl.class);

    @Override
    public Solution finishTestsSolution(AnswersList answersList) {
        Solution solution = new Solution();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        solution.setUser(userServiceImpl.getUserByLogin(login));
        solution.setTest(answersList.getAnswers().get(0).getTest());

        answersList.getAnswers().forEach((question) -> {
            if (question.isRight()) {
                solution.setCountOfRightAnswers(solution.getCountOfRightAnswers() + 1);
            }
            solution.setCountOfQuestions(solution.getCountOfQuestions() + 1);
        });

        solution.rating();
        solution.getTest().setCountOfSolutions(solution.getTest().getCountOfSolutions() + 1);
        solutionServiceImpl.saveSolution(solution);

        logger.info("Solution was saved");

        if (solution.getTest().getCreator().isDoSend()) {
            sendServiceImpl.send(solution);
            logger.info("Result was sent on " + solution.getTest().getCreator().getEmail());
        }

        return solution;
    }

    @Override
    public AnswersList createQuestion(Test test) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        test.setCreator(userServiceImpl.getUserByLogin(login));

        AnswersList answersList = new AnswersList(new ArrayList<>());
        int count = test.getCountOfQuestions();
        for (int i = 0; i < count; i++) {
            answersList.getAnswers().add(new Question());
            answersList.getAnswers().get(i).setTest(test);
            answersList.getAnswers().get(i).setNumOfQuestion(i + 1);
        }
        logger.info("Questions was created");

        return answersList;
    }

    @Override
    public ByteArrayOutputStream saveResults(int id){

        List<Solution> list = solutionServiceImpl.getSolutionsByTestId(id);

        System.out.println(list);

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

        logger.debug("PDF was created");

        return outputStream;
    }

    @Override
    public void getTests(Model model, String searchTitle, int page){
//        if ((searchTitle == null) || (searchTitle.equals(""))) {
//            model.addAttribute("tests", testServiceImpl.getAllTests());
//        } else {
//            model.addAttribute("tests", testServiceImpl.getTestsByTitle(searchTitle));
//        }

        if ((searchTitle == null) || (searchTitle.equals(""))) {
            model.addAttribute("tests", testServiceImpl.getAllTests(PageRequest.of(page, 6)));
        } else {
            model.addAttribute("tests", testServiceImpl.getTestsByTitle(searchTitle,
                    PageRequest.of(page, 6)));
        }

        logger.debug("All tests was got");
    }

    @Override
    public void getStatistic(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        int id = userServiceImpl.getUserByLogin(login).getId();

        List<Solution> solutions = solutionServiceImpl.getSolutionsByUserId(id);

        model.addAttribute("solutions", solutions);

        logger.debug("Statistic was got");
    }

    @Override
    public void saveTest(AnswersList answersList){
        Test test = answersList.getAnswers().get(0).getTest();

        testServiceImpl.saveTest(test);

        answersList.getAnswers().forEach(question -> {
            question.setTest(test);
            questionServiceImpl.saveQuestion(question);
        });

        logger.info("Test was saved");
    }

    @Override
    public void getAllMyTests(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userServiceImpl.getUserByLogin(login);

        model.addAttribute("tests", testServiceImpl.getAllTestsByCreatorId(user.getId()));

        logger.debug("My tests was got");
    }

    @Override
    public void getTestsCard(Model model, int id){
        AnswersList answersList = new AnswersList(questionServiceImpl.getQuestionsList(id));

        model.addAttribute("answersList", answersList);

        logger.debug("Test's card was opened");
    }

    @Override
    public void getMyTestsSolutions(Model model, int id){
        Test test = testServiceImpl.getTestById(id);
        List<Solution> solutions = solutionServiceImpl.getSolutionsByTestId(test.getId());

        model.addAttribute("test", test);
        model.addAttribute("solutions", solutions);

        logger.debug("My tests solutions was got");
    }

    @Override
    public void deleteTest(int id) {
        testServiceImpl.deleteTestById(id);

        logger.info("Test '" + testServiceImpl.getTestById(id).getTitle() + "' was deleted");
    }

}
