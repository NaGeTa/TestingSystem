package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.mapper.SolutionMapper;
import com.example.testingsystem.model.BusinessException;
import com.example.testingsystem.model.ResetForMail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@AllArgsConstructor
public class SendServiceImpl implements SendService{

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SolutionMapper solutionMapper;
    private final ObjectMapper objectMapper;
    private final static Logger logger = Logger.getLogger(SendServiceImpl.class);

//    @Override
    public void send(Solution solution){
        Runnable r = () -> {
            try {
                restTemplate.postForEntity(new URI("http://localhost:8090/sendSolution"), solutionMapper.toMail(solution), Object.class);

//                kafkaTemplate.send("resultTopic", "result", objectMapper.writeValueAsString(solutionMapper.toMail(solution)));

                System.out.println("\u001B[32m Письмо успешно отправлено " + "\u001B[0m");
                logger.info("Result was sent on mail");

            } catch (Exception e) {
                System.out.println("\u001B[31m Ошибка при отправке письма " + "\u001B[0m");
                logger.info("Error when result sending on mail");

                throw new BusinessException("Error when result sending");
            }

        };

        Thread thread = new Thread(r, "SolutionMailThread");
        thread.start();
    }

//    @Override
    public void sendLogin(ResetForMail resetForMail) {
        Runnable r = () -> {
            try {
                restTemplate.postForEntity(new URI("http://localhost:8090/sendReset"), resetForMail, Object.class);

//                kafkaTemplate.send("resetTopic", "reset", objectMapper.writeValueAsString(resetForMail));

                System.out.println("\u001B[32m Письмо успешно отправлено " + "\u001B[0m");
                logger.info("Result was sent on mail");

            } catch (Exception e) {
                System.out.println("\u001B[31m Ошибка при отправке письма " + "\u001B[0m");
                logger.info("Error when reset-mail sending");

                throw new BusinessException("Error when reset mail sending: " + e.getMessage());
            }

        };

        Thread thread = new Thread(r, "ResetMailThread");
        thread.start();
    }
}
