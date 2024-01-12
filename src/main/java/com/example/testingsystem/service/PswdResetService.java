package com.example.testingsystem.service;

import org.springframework.ui.Model;

public interface PswdResetService {

    String resetPassword(String login, Model model);
}
