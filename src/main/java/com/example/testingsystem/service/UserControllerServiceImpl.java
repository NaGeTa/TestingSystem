package com.example.testingsystem.service;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
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
        User user = userServiceImpl.getUserByLogin(userServiceImpl.getCurrUsername());

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

    @Override
    public String changePswd(String newPass1, String newPass2, String pass, Model model) {

        if (!userServiceImpl.isPswdCorrect(pass, userServiceImpl.getUserByLogin(userServiceImpl.getCurrUsername()).getPassword())){
            model.addAttribute("passError", "Неверный пароль");
            return "user/pswd_change";
        }

        if (newPass1.length()==0 || newPass2.length()==0 || (!newPass1.equals(newPass2))){
            model.addAttribute("newPassError", "Пароли не совпадают");
            return "user/pswd_change";
        }

        User user = userServiceImpl.getUserByLogin(userServiceImpl.getCurrUsername());
        user.setPassword(newPass1);
        userServiceImpl.save(user);

        logger.info(userServiceImpl.getCurrUsername() + " change password");

        return "redirect:/profile";
    }
}
