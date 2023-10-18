package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.repository.SolutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolutionServiceImpl implements SolutionService{

    private final SolutionRepository solutionRepository;

    @Override
    public void saveSolution(Solution solution){
        solutionRepository.save(solution);
    }

    @Override
    public List<Solution> getSolutionsByUserId(int id){
        return solutionRepository.findAllByUserId(id);
    }

    @Override
    public List<Solution> getSolutionsByTestId(int id){
        return solutionRepository.findAllByTestId(id);
    }

    @Override
    public int getCountOfSolutionsByUserId(int id){
        return solutionRepository.countSolutionByUserId(id);
    }
}
