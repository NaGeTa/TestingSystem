package com.example.testingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetForMail {
    private String mail;
    private String URL;
}
