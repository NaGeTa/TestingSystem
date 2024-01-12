package com.example.testingsystem.controller;

import com.example.testingsystem.entity.User;
import com.example.testingsystem.service.AuthControllerServiceImpl;
import com.example.testingsystem.service.PswdResetServiceImpl;
import com.example.testingsystem.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final PswdResetServiceImpl pswdResetService;
    private final AuthControllerServiceImpl authControllerService;
    private final static Logger logger = Logger.getLogger(AuthController.class);

    @GetMapping("/")
    public String getMainPage() {
        return "logic/main";
    }

    @GetMapping("/menu")
    public String getMenuPage() {
        return "logic/menu";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "logic/login";
    }

    @GetMapping("/registration")
    public String getRegPage(Model model) {
        model.addAttribute("user", new User());
        return "logic/registration";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {

        if (userServiceImpl.countUsersByEmail(user.getEmail()) > 0) {
            bindingResult.addError(new FieldError("user.getEmail()", "email",
                    "Данная почта уже занята"));
        }

        if (userServiceImpl.countUsersByLogin(user.getLogin()) > 0) {
            bindingResult.addError(new FieldError("user.getLogin()", "login",
                    "Данный логин уже занят"));
        }

        if (bindingResult.hasErrors()) {
            return "/logic/registration";
        }
        userServiceImpl.save(user);

        logger.info("New user '" + user.getLogin() + "' was created");

        return "redirect:/login";
    }

    @GetMapping("/passwordReset")
    public String getPswdResetPage() {
        return "logic/login_for_reset";
    }

    @PostMapping("/passwordReset")
    public String sendPswdReset(@RequestParam("login") String login, Model model) {

        return pswdResetService.resetPassword(login, model);
    }

    @GetMapping("/passwordReset/{encryptedLogin}")
    public String getPswdReset(@PathVariable("encryptedLogin") String encryptedLogin, Model model) {

        model.addAttribute("encryptedLogin", encryptedLogin);

        return "logic/password_reset";
    }

    @PostMapping("/passwordReset/{encryptedLogin}")
    public String pswdReset(@PathVariable("encryptedLogin") String encryptedLogin, @RequestParam("newPassword1") String pass1,
                            @RequestParam("newPassword2") String pass2, Model model) {

        return authControllerService.pswdReset(encryptedLogin, pass1, pass2, model);

    }
}
