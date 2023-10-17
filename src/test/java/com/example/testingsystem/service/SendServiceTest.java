package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.mapper.SolutionMapper;
import com.example.testingsystem.model.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@AllArgsConstructor
public class SendServiceTest {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SolutionMapper solutionMapper;
    private final ObjectMapper objectMapper;

    public void send(Solution solution){
        Runnable r = () -> {
            try {
                restTemplate.postForEntity(new URI("http://localhost:8090/"), solutionMapper.toMail(solution), Object.class);

//                kafkaTemplate.send("mailTopic", "mail", objectMapper.writeValueAsString(solutionMapper.toMail(solution)));

            } catch (Exception e) {
                throw new BusinessException("Error when result sending");
            }
            System.out.println("\u001B[32m Письмо успешно отправлено " + "\u001B[0m");
        };

        Thread thread = new Thread(r, "MailThread");
        thread.start();
    }
}
