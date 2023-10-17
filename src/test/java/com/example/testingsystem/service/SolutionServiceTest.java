package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.repository.SolutionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SolutionServiceTest {

    @Mock
    SolutionRepository solutionRepository;

    @Test
    public void saveSolution(Solution solution){
        solutionRepository.save(solution);

        Mockito.verify(solutionRepository).save(solution);
    }

    @Test
    public void getSolutionsByUserId(int id){
        solutionRepository.findAllByUserId(id);

        Mockito.verify(solutionRepository).findAllByUserId(id);
    }

    @Test
    public void getSolutionsByTestId(int id){
        solutionRepository.findAllByTestId(id);

        Mockito.verify(solutionRepository).findAllByTestId(id);
    }

    @Test
    public void getCountOfSolutionsByUserId(int id){
        solutionRepository.countSolutionByUserId(id);

        Mockito.verify(solutionRepository).countSolutionByUserId(id);
    }
}
