package com.example.testingsystem.service;

import com.example.testingsystem.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class AuthControllerServiceImpl implements AuthControllerService{

    private final EncryptionServiceImpl encryptionService;
    private final UserService userService;

    @Override
    public String pswdReset(String encryptedLogin, String newPass1, String newPass2, Model model) {

        if (newPass1.length()==0 || newPass2.length()==0 || (!newPass1.equals(newPass2))){
            model.addAttribute("newPassError", "Пароли не совпадают");
            return "user/pswd_change";
        }

        System.out.println(encryptedLogin);
        System.out.println(encryptionService.decrypt(encryptedLogin));

        String login = encryptionService.decrypt(encryptedLogin);
        User user = userService.getUserByLogin(login);
        user.setPassword(newPass1);
        userService.save(user);

        return "redirect:/login";
    }
}
