package com.example.testingsystem.service;

import com.example.testingsystem.entity.Solution;
import com.example.testingsystem.model.ResetForMail;

public interface SendService {
    void send(Solution solution);
    void sendLogin(ResetForMail resetForMail);
}
