package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;

import java.util.List;

public interface SolutionService {
    void saveSolution(Solution solution);
    List<Solution> getSolutionsByUserId(int id);
    List<Solution> getSolutionsByTestId(int id);
    int getCountOfSolutionsByUserId(int id);
}
