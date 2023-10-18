package com.example.testingsystem.model;

import com.example.testingsystem.entity.Question;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class AnswersList {

    @Valid
    private List<Question> answers;

    public AnswersList(List<Question> answers){
        this.answers=answers;
    }

    public List<Question> getAnswers(){
        return answers;
    }
}
