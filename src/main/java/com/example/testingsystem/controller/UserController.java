package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.service.SolutionService;
import com.example.testingsystem.service.TestService;
import com.example.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final SolutionService solutionService;
    private final TestService testService;

    @GetMapping("profile")
    public String getProfile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userService.getUserByLogin(login);

        int countOfSolutions = solutionService.getCountOfSolutionsByUserId(user.getId());

        if(user.getRole()!= Role.STUDENT_ROLE){
            int countOfCreatedTests = testService.getCountOfCreatedTests(user.getId());
            model.addAttribute("countOfCreatedTests", countOfCreatedTests);
        }

        model.addAttribute("user", user);
        model.addAttribute("countOfSolutions", countOfSolutions);

        return "user/profile";
    }

    @GetMapping("/users")
    public String getUsers(Model model){

        model.addAttribute("users", userService.getAllUsers());

        return "user/users";
    }

    @GetMapping("/users/{id}")
    public String getUserCard(@PathVariable int id, Model model){ ////////////////////////////////fdsfdfs

        model.addAttribute("user", userService.getUserById(id));

        return "user/profile";

    }
}
