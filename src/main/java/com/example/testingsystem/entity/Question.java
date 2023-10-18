package com.example.testingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotBlank(message = "Поле для вопроса не может быть пустым")
    private String title;

    @Column(name = "number_of_question")
    private int numOfQuestion;

    @Column(name = "correct_answer_id")
    private int rightAnswer;

    @Column(name = "first_answer")
    @NotBlank(message = "Вариант ответа не может быть пустым")
    private String firstAnswer;

    @Column(name = "second_answer")
    @NotBlank(message = "Вариант ответа не может быть пустым")
    private String secondAnswer;

    @Column(name = "third_answer")
    @NotBlank(message = "Вариант ответа не может быть пустым")
    private String thirdAnswer;

    @Column(name = "fourth_answer")
    @NotBlank(message = "Вариант ответа не может быть пустым")
    private String fourthAnswer;

    @ManyToOne
    @JoinColumn(name = "tests_id")
    private Test test;

    @Transient
    private int choiceAnswer;

    public boolean isRight() {
        if (choiceAnswer == 0) {
            return false;
        }
        return choiceAnswer == rightAnswer;
    }
}
