package com.example.testingsystem.controller;

import com.example.testingsystem.entity.User;
import com.example.testingsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;

    @GetMapping("/")
    public String getMainPage(){
        return "logic/main";
    }

    @GetMapping("/menu")
    public String getMenuPage(){
        return "logic/menu";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "logic/login";
    }

    @GetMapping("/registration")
    public String getRegPage(Model model){
        model.addAttribute("user", new User());
        return "logic/registration";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") @Valid  User user,
                           BindingResult bindingResult){

        if(userService.countUsersByEmail(user.getEmail())>0){
            bindingResult.addError(new FieldError("user.getEmail()", "email",
                    "Данная почта уже занята"));
        }

        if(userService.countUsersByLogin(user.getLogin())>0){
            bindingResult.addError(new FieldError("user.getLogin()", "login",
                    "Данный логин уже занят"));
        }

        if(bindingResult.hasErrors()){
            return "/logic/registration";
        }
        userService.save(user);

        return "redirect:/login";
    }

}
