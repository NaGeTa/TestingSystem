package com.example.testingsystem.controller;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.service.SolutionServiceImpl;
import com.example.testingsystem.service.TestServiceImpl;
import com.example.testingsystem.service.UserControllerServiceImpl;
import com.example.testingsystem.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final SolutionServiceImpl solutionServiceImpl;
    private final TestServiceImpl testServiceImpl;
    private final UserControllerServiceImpl userControllerServiceImpl;

    @GetMapping("/profile")
    public String getProfile(Model model) {

        userControllerServiceImpl.getProfile(model);

        return "user/profile";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {

        if(!userServiceImpl.hasAccess(Role.ADMIN_ROLE)){
            return "logic/error";
        }

        model.addAttribute("users", userServiceImpl.getAllUsers());

        return "user/users";
    }

    @GetMapping("/users/{id}")
    public String getUserCard(@PathVariable int id, Model model) {

        if(!userServiceImpl.hasAccess(Role.ADMIN_ROLE)){
            return "logic/error";
        }

        User user = userServiceImpl.getUserById(id);
        int countOfSolutions = solutionServiceImpl.getCountOfSolutionsByUserId(user.getId());
        int countOfCreatedTests = testServiceImpl.getCountOfCreatedTests(user.getId());

        model.addAttribute("countOfCreatedTests", countOfCreatedTests);
        model.addAttribute("countOfSolutions", countOfSolutions);
        model.addAttribute("user", userServiceImpl.getUserById(id));

        return "user/users_profile";

    }

    @PostMapping("users/{id}")
    public String banUser(@PathVariable int id, @ModelAttribute("user") User userForRole){

        if(!userServiceImpl.hasAccess(Role.ADMIN_ROLE)){
            return "logic/error";
        }

        return "redirect:/users/" + userControllerServiceImpl.banUser(id, userForRole).getId();
    }

    @PostMapping("/updateProfile/{id}")
    public String updateProfile(@ModelAttribute("user") User user, @PathVariable int id){

        userControllerServiceImpl.updateProfile(id, user);

        return "redirect:/profile";
    }

    @GetMapping("/changePassword")
    public String getPswdChangePage(Model model){

        model.addAttribute("user", new User());

        return "user/pswd_change";
    }

    @PostMapping("/changePassword")
    public String changePswd(@RequestParam("newPassword1") String newPass1, @RequestParam("newPassword2") String newPass2,
                           @RequestParam("password") String pass, Model model){

        return userControllerServiceImpl.changePswd(newPass1, newPass2, pass, model);
    }

}
