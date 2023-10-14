package com.example.testingsystem.service;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class UserControllerService {

    private final UserService userService;
    private final SolutionService solutionService;
    private final TestService testService;
    private static final Logger logger = Logger.getLogger(UserControllerService.class);

    public void getProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userService.getUserByLogin(login);

        int countOfSolutions = solutionService.getCountOfSolutionsByUserId(user.getId());

        if (user.getRole() != Role.STUDENT_ROLE) {
            int countOfCreatedTests = testService.getCountOfCreatedTests(user.getId());
            model.addAttribute("countOfCreatedTests", countOfCreatedTests);
        }

        model.addAttribute("user", user);
        model.addAttribute("countOfSolutions", countOfSolutions);

        logger.debug("Profile was got");
    }

    public User banUser(int id, User userForRole){
        User user = userService.getUserById(id);

        if(!user.isBlocked()){
            user.setRole(Role.BLOCKED);
            logger.info(user.getLogin() + " was blocked");

        } else{
            user.setRole(userForRole.getRole());
            logger.info(user.getLogin() + " was unblocked");
        }

        userService.update(user);

        return user;
    }


    public void updateProfile(int id, User user) {
        User userForSave = userService.getUserById(id);

        userForSave.setDoSend(user.isDoSend());

        userService.update(userForSave);

        logger.info(user.getLogin() + " was update");
    }
}
