package com.example.testingsystem.service;

import com.example.testingsystem.entity.User;
import com.example.testingsystem.model.ResetForMail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class PswdResetServiceImpl implements PswdResetService{

    private final UserServiceImpl userServiceImpl;
    private final SendServiceImpl sendServiceImpl;
    private final EncryptionServiceImpl encryptionServiceImpl;

    @Override
    public String resetPassword(String login, Model model) {

        if (userServiceImpl.countUsersByLogin(login) == 0){
            model.addAttribute("error", "Пользователь с таким логином не найден");
            return "logic/pswd_reset";
        }

        User user = userServiceImpl.getUserByLogin(login);
        String code = encryptionServiceImpl.encrypt(login);

        String URL = "http://localhost:8080/passwordReset/" + code;

        sendServiceImpl.sendLogin(new ResetForMail(user.getEmail(), URL));

        return "logic/ok";
    }
}
