package com.example.testingsystem.service;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserControllerServiceImplTest {

    @Autowired
    UserControllerServiceImpl userControllerServiceImpl;

    @MockBean
    UserServiceImpl userServiceImpl;
    @MockBean
    SolutionServiceImpl solutionServiceImpl;
    @MockBean
    TestServiceImpl testServiceImpl;
    @MockBean
    Authentication authentication;
    @MockBean
    Model model;

    @Test
    public void getProfile(){
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String login = "nickname";
        int count = 1;
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);

        Mockito.when(authentication.getName()).thenReturn(login);
        Mockito.when(userServiceImpl.getUserByLogin(login)).thenReturn(user);
        Mockito.when(solutionServiceImpl.getCountOfSolutionsByUserId(user.getId())).thenReturn(count);

        userControllerServiceImpl.getProfile(model);

        Mockito.verify(model).addAttribute("user", user);
        Mockito.verify(model).addAttribute("countOfSolutions", count);
    }

    @Test
    public void banUser(){
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.BLOCKED, false);
        User userForRole = new User(2, " ", " ", " ", " ", " ", new Date(), null, Role.ADMIN_ROLE, false);
        User userTest = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.ADMIN_ROLE, false);
        int id = 1;

        Mockito.when(userServiceImpl.getUserById(id)).thenReturn(user);

        User returnedUser = userControllerServiceImpl.banUser(id, userForRole);

        Assertions.assertEquals(returnedUser, userTest);
    }

    @Test
    public void updateProfile() {
        User user = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, false);
        User userForDoSend = new User(1, " ", " ", " ", " ", " ", new Date(), null, Role.STUDENT_ROLE, true);
        int id = 1;

        Mockito.when(userServiceImpl.getUserById(id)).thenReturn(user);

        userControllerServiceImpl.updateProfile(id, userForDoSend);

        Mockito.verify(userServiceImpl).update(user);
    }
}
