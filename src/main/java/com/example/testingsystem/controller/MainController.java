package com.example.testingsystem.controller;

import com.example.testingsystem.entity.User;
import com.example.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;

    @GetMapping("/")
    public String getMenuPage(){
        return "menu";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String getRegPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") User user){
        userService.save(user);

//        return "redirect:/login";
        return "/login";
    }

}
