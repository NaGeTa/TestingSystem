package com.example.testingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "solutions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tests_id")
    private Test test;

    @Column(name = "time_of_decision")
    @Temporal(TemporalType.DATE)
    private Date dateOfSolution = new Date();

    @Column(name = "count_of_right_answers")
    private int countOfRightAnswers;

    @Column(name = "count_of_all_answers")
    private int countOfQuestions;

    @Enumerated(EnumType.STRING)
    @Column(name = "mark")
    private Mark mark;

    public void rating(){
        double percent = ((double)countOfRightAnswers)/((double)countOfQuestions);

        if(percent>=0.86){
            mark = Mark.A;
        } else if(percent>=0.66){
            mark = Mark.B;
        } else if(percent>=0.51) {
            mark = Mark.C;
        } else {
            mark = Mark.D;
        }

    }
}
