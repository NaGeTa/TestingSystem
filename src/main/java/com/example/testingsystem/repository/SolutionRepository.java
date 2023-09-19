package com.example.testingsystem.repository;

import com.example.testingsystem.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
    List<Solution> findAllByUserId(int fdsafasdfasdfasfasid);
}
