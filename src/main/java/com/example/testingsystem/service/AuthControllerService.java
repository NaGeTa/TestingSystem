package com.example.testingsystem.service;

import org.springframework.ui.Model;

public interface AuthControllerService {
    String pswdReset(String encryptedLogin, String newPass1, String newPass2, Model model);
}
