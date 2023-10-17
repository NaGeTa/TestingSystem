package com.example.testingsystem.service;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    private BCryptPasswordEncoder encoder(){
       return new BCryptPasswordEncoder();
    }

    @Test
    public void save(User user){
        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void update(User user){
        userRepository.save(user);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void countUsersByLogin(String value){
        userRepository.countUsersByLogin(value);

        Mockito.verify(userRepository).countUsersByLogin(value);
    }

    @Test
    public void countUsersByEmail(String value) {
        userRepository.countUsersByEmail(value);

        Mockito.verify(userRepository).countUsersByEmail(value);
    }

    @Test
    public void getUserByLogin(String login){
        userRepository.findByLogin(login).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        Mockito.verify(userRepository).findByLogin(login);
    }

    @Test
    public void getAllUsers(){
        userRepository.findAll();

        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void getUserById(int id){
        userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        Mockito.verify(userRepository).findById(id);
    }

    @Test
    public void hasAccess(){
        List<Role> roles = List.of(Role.STUDENT_ROLE);
        String login = "";

        Mockito.when(userRepository.findByLogin(login))
                .thenReturn(Optional.of(new User(1, null, null, null, null, null, new Date(), null, Role.STUDENT_ROLE, false)));

        User user;
        if(userRepository.findByLogin(login).isPresent()) {
            user = userRepository.findByLogin(login).get();
            boolean isOption = roles.contains(user.getRole());
            Assertions.assertTrue(isOption);
        }

    }
}
