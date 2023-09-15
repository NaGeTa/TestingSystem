package com.example.testingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "questions")
@Getter
@Setter
@ToString
public class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "number_of_question")
    private int numOfQuestion;

    @Column(name = "correct_answer_id")
    private int rightAnswer;

    @Column(name = "first_answer")
    private String firstAnswer;

    @Column(name = "second_answer")
    private String secondAnswer;

    @Column(name = "third_answer")
    private String thirdAnswer;

    @Column(name = "fourth_answer")
    private String fourthAnswer;

    @ManyToOne
    @JoinColumn(name = "tests_id")
    private Test test;

    @Transient
    private int choiceAnswer;

    public boolean isRight(){
        if(choiceAnswer==0){
            return false;
        }
        return choiceAnswer==rightAnswer;
    }
}
