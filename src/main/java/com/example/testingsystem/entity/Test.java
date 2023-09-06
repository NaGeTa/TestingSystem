package com.example.testingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tests")
@Getter
@Setter
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(name = "category_id")
    private TestsCategory category;

    @Column(name = "count_of_questions")
    private int count_of_questions;

    @Column(name = "count_of_decisions")
    private int count_of_solutions;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_of_creation = new Date();

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;
}
