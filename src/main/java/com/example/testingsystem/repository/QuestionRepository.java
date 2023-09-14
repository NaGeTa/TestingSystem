package com.example.testingsystem.repository;

import com.example.testingsystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findQuestionsByTestId(int id);
}
