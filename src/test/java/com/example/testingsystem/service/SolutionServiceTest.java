package com.example.testingsystem.service;

import com.example.testingsystem.entity.*;
import com.example.testingsystem.repository.SolutionRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SolutionServiceTest {

    @Autowired
    SolutionService solutionService;

    @MockBean
    SolutionRepository solutionRepository;

    @org.junit.jupiter.api.Test
    public void saveSolution(){
        Test test = new Test(1, " ", 0, 0, new Date(), null, Category.BIOLOGY);
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        Solution solution = new Solution(1, user, test, new Date(), 0, 0, Mark.A);

        solutionRepository.save(solution);

        Mockito.verify(solutionRepository).save(solution);
    }

    @org.junit.jupiter.api.Test
    public void getSolutionsByUserId(){
        int id = 1;

        solutionRepository.findAllByUserId(id);

        Mockito.verify(solutionRepository).findAllByUserId(id);
    }

    @org.junit.jupiter.api.Test
    public void getSolutionsByTestId(){
        int id = 1;

        solutionRepository.findAllByTestId(id);

        Mockito.verify(solutionRepository).findAllByTestId(id);
    }

    @org.junit.jupiter.api.Test
    public void getCountOfSolutionsByUserId(){
        int id = 1;

        solutionRepository.countSolutionByUserId(id);

        Mockito.verify(solutionRepository).countSolutionByUserId(id);
    }
}
