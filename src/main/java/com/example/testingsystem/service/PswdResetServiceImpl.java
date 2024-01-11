package com.example.testingsystem.service;

import com.example.testingsystem.entity.User;
import com.example.testingsystem.model.ResetForMail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PswdResetServiceImpl implements PswdResetService{

    private final UserServiceImpl userService;
    private final SendServiceImpl sendService;

    @Override
    public void resetPassword(String login) {

        User user = userService.getUserByLogin(login);
        String password = user.getPassword();

        String URL = "http://localhost:8080/resetPassword/" + password;

        sendService.sendPassword(/*new ResetForMail(user.getEmail(), URL)*/ user.getEmail(), URL);
//        sendService.sendPassword("213");
    }
}
