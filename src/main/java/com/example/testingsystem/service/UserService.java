package com.example.testingsystem.service;

import com.example.testingsystem.entity.Role;
import com.example.testingsystem.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    int countUsersByLogin(String value);
    int countUsersByEmail(String value);
    User getUserByLogin(String login);
    List<User> getAllUsers();
    User getUserById(int id);
    boolean hasAccess(Role... role);
}
