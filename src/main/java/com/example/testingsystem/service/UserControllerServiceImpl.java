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
public class UserControllerServiceImpl implements UserControllerService{

    private final UserServiceImpl userServiceImpl;
    private final SolutionServiceImpl solutionServiceImpl;
    private final TestServiceImpl testServiceImpl;
    private static final Logger logger = Logger.getLogger(UserControllerServiceImpl.class);

    @Override
    public void getProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userServiceImpl.getUserByLogin(login);

        int countOfSolutions = solutionServiceImpl.getCountOfSolutionsByUserId(user.getId());

        if (user.getRole() != Role.STUDENT_ROLE) {
            int countOfCreatedTests = testServiceImpl.getCountOfCreatedTests(user.getId());
            model.addAttribute("countOfCreatedTests", countOfCreatedTests);
        }

        model.addAttribute("user", user);
        model.addAttribute("countOfSolutions", countOfSolutions);

        logger.debug("Profile was got");
    }

    @Override
    public User banUser(int id, User userForRole){
        User user = userServiceImpl.getUserById(id);

        if(!user.isBlocked()){
            user.setRole(Role.BLOCKED);
            logger.info(user.getLogin() + " was blocked");

        } else{
            user.setRole(userForRole.getRole());
            logger.info(user.getLogin() + " was unblocked");
        }

        userServiceImpl.update(user);

        return user;
    }

    @Override
    public void updateProfile(int id, User user) {
        User userForSave = userServiceImpl.getUserById(id);

        userForSave.setDoSend(user.isDoSend());

        userServiceImpl.update(userForSave);

        logger.info(user.getLogin() + " was update");
    }
}
