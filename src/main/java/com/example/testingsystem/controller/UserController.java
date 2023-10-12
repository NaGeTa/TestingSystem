package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.service.SolutionService;
import com.example.testingsystem.service.TestService;
import com.example.testingsystem.service.UserControllerService;
import com.example.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final SolutionService solutionService;
    private final TestService testService;
    private final UserControllerService userControllerService;

    @GetMapping("/profile")
    public String getProfile(Model model) {

        userControllerService.getProfile(model);

        return "user/profile";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {

        if(!userService.hasAccess(Role.ADMIN_ROLE)){
            return "logic/error";
        }

        model.addAttribute("users", userService.getAllUsers());

        return "user/users";
    }

    @GetMapping("/users/{id}")
    public String getUserCard(@PathVariable int id, Model model) {

        if(!userService.hasAccess(Role.ADMIN_ROLE)){
            return "logic/error";
        }

        User user = userService.getUserById(id);
        int countOfSolutions = solutionService.getCountOfSolutionsByUserId(user.getId());
        int countOfCreatedTests = testService.getCountOfCreatedTests(user.getId());

        model.addAttribute("countOfCreatedTests", countOfCreatedTests);
        model.addAttribute("countOfSolutions", countOfSolutions);
        model.addAttribute("user", userService.getUserById(id));

        return "user/users_profile";

    }

    @PostMapping("users/{id}")
    public String banUser(@PathVariable int id, @ModelAttribute("user") User userForRole){

        if(!userService.hasAccess(Role.ADMIN_ROLE)){
            return "logic/error";
        }

        return "redirect:/users/" + userControllerService.banUser(id, userForRole).getId();
    }

    @PostMapping("/updateProfile/{id}")
    public String updateProfile(@ModelAttribute("user") User user, @PathVariable int id){

        userControllerService.updateProfile(id, user);

        return "redirect:/profile";
    }

}
