package com.example.testingsystem.model;

import com.example.testingsystem.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswersList {
    private List<Question> answers;

    public AnswersList(List<Question> answers){
        this.answers=answers;
    }

    public List<Question> getAnswers(){
        return answers;
    }
}
