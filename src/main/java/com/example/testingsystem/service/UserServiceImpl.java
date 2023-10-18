package com.example.testingsystem.service;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;
import com.example.testingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private BCryptPasswordEncoder encoder(){
       return new BCryptPasswordEncoder();
    }

    @Override
    public void save(User user){
        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(User user){
        userRepository.save(user);
    }

    @Override
    public int countUsersByLogin(String value){
        return userRepository.countUsersByLogin(value);
    }

    @Override
    public int countUsersByEmail(String value) {
        return userRepository.countUsersByEmail(value);
    }

    @Override
    public User getUserByLogin(String login){
        return userRepository.findByLogin(login).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    @Override
    public boolean hasAccess(Role ... role){
        List<Role> roles = List.of(role);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = getUserByLogin(login);

        return roles.contains(user.getRole());
    }
}
