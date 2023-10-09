package com.example.testingsystem.service;

import com.example.testingsystem.entity.Question;
import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.entity.Test;
import com.example.testingsystem.model.AnswersList;
import com.example.testingsystem.model.SolutionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestsControllerService {

    private final UserService userService;
    private final SolutionService solutionService;
    private final RestTemplate restTemplate;
    private final SolutionMapper solutionMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

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
            Runnable r = () -> {
                try {
                    restTemplate.postForEntity(new URI("http://localhost:8090/"), solutionMapper.toMail(solution), Object.class);

//                kafkaTemplate.send("mailTopic", "mail", objectMapper.writeValueAsString(solutionMapper.toMail(solution)));

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("\u001B[32m Письмо успешно отправлено " + "\u001B[0m");
            };

            Thread thread = new Thread(r, "MailThread");
            thread.start();
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

        if(list.isEmpty())
            return null;

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
                    + "\n Количество правильных ответов: " + solution.getCountOfRightAnswers(), font);
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }

        document.close();

        return outputStream;
    }
}
