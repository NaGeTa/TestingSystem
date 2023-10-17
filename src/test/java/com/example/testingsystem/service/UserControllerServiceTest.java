//package com.example.testingsystem.service;
//
//import com.example.testingsystem.entity.Role;
//import com.example.testingsystem.entity.User;
//import lombok.AllArgsConstructor;
//import org.apache.log4j.Logger;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//
//import java.util.Date;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerServiceTest {
//
//    @Mock
//    UserService userService;
//    @Mock
//    SolutionService solutionService;
//    @Mock
//    TestService testService;
//
//    public void getProfile(Model model){
//        String login = "";
//
//        Mockito.when(userService.getUserByLogin(login))
//                .thenReturn(new User(1, null, null, null, null, null, new Date(), null, Role.STUDENT_ROLE, false));
//
//
//        int countOfSolutions = solutionService.getCountOfSolutionsByUserId(user.getId());
//
//        if (user.getRole() != Role.STUDENT_ROLE) {
//            int countOfCreatedTests = testService.getCountOfCreatedTests(user.getId());
//            model.addAttribute("countOfCreatedTests", countOfCreatedTests);
//        }
//
//        model.addAttribute("user", user);
//        model.addAttribute("countOfSolutions", countOfSolutions);
//
//    }
//
//    public User banUser(int id, User userForRole){
//        User user = userService.getUserById(id);
//
//        if(!user.isBlocked()){
//            user.setRole(Role.BLOCKED);
//            logger.info(user.getLogin() + " was blocked");
//
//        } else{
//            user.setRole(userForRole.getRole());
//            logger.info(user.getLogin() + " was unblocked");
//        }
//
//        userService.update(user);
//
//        return user;
//    }
//
//
//    public void updateProfile(int id, User user) {
//        User userForSave = userService.getUserById(id);
//
//        userForSave.setDoSend(user.isDoSend());
//
//        userService.update(userForSave);
//
//        logger.info(user.getLogin() + " was update");
//    }
//}
