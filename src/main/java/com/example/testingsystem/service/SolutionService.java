package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.repository.SolutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public void saveSolution(Solution solution){
        solutionRepository.save(solution);
    }

    public List<Solution> getSolutionsByUserId(int id){
        return solutionRepository.findAllByUserId(id);
    }
}
