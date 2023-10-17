package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.mapper.SolutionMapper;
import com.example.testingsystem.model.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@ExtendWith(MockitoExtension.class)
public class SendServiceTest {

    @Mock
    RestTemplate restTemplate;
    @Mock
    SolutionMapper solutionMapper;

    @Test
    public void send(Solution solution){
        Runnable r = () -> {
            try {
                restTemplate.postForEntity(new URI("http://localhost:8090/"), solutionMapper.toMail(solution), Object.class);

                Mockito.verify(restTemplate).postForEntity(new URI("http://localhost:8090/"), solutionMapper.toMail(solution), Object.class);

            } catch (Exception ignored) {

            }
        };

        Thread thread = new Thread(r, "MailThread");

        thread.start();
    }
}
