package com.example.testingsystem.service;

import com.example.testingsystem.entity.*;
import com.example.testingsystem.mapper.SolutionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SendServiceTest {

    @Autowired
    SendService sendService;

    @MockBean
    RestTemplate restTemplate;
    @MockBean
    SolutionMapper solutionMapper;

    @Test
    public void send() throws URISyntaxException {
        com.example.testingsystem.entity.Test test = new com.example.testingsystem.entity.Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        Solution solution = new Solution(1, user, test, new Date(), 0, 0, Mark.A);

        sendService.send(solution);

        Mockito.verify(restTemplate).postForEntity(new URI("http://localhost:8090/"), solutionMapper.toMail(solution),
                Object.class);
    }
}
