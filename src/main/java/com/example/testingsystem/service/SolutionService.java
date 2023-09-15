package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.repository.SolutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public void saveSolution(Solution solution){
        solutionRepository.save(solution);
    }
}
