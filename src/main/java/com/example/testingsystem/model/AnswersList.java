package com.example.testingsystem.model;

import com.example.testingsystem.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
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
