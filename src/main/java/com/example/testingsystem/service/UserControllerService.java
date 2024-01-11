package com.example.testingsystem.service;

import com.example.testingsystem.entity.User;
import org.springframework.ui.Model;

public interface UserControllerService {
    void getProfile(Model model);
    User banUser(int id, User userForRole);
    void updateProfile(int id, User user);
    String changePswd(String newPass1, String newPass2, String pass, Model model);
}
