package com.example.testingsystem.model;

import com.example.testingsystem.entity.Mark;
import lombok.Data;

@Data
public class SolutionForMail {
    private String firstName;
    private String lastName;
    private String title;
    private String creatorsMail;
    private Mark mark;
}
