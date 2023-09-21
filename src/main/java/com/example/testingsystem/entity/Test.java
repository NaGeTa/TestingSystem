package com.example.testingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "tests")
@Getter
@Setter
@ToString
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "count_of_questions")
    private int countOfQuestions;

    @Column(name = "count_of_decisions")
    private int countOfSolutions;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation = new Date(); //проверить при получении из БД меняется ли дата, сказать балычу

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private Category category;
}
