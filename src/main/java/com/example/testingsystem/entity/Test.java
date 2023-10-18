package com.example.testingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotBlank(message = "Тест должен иметь название")
    private String title;

    @Column(name = "count_of_questions")
    @Min(value = 1, message = "Кол-во вопросов должно начинаться от 1")
    private int countOfQuestions;

    @Column(name = "count_of_decisions")
    private int countOfSolutions;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation = new Date(); //проверить при получении из БД меняется ли дата, сказать балычу

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private Category category;
}
