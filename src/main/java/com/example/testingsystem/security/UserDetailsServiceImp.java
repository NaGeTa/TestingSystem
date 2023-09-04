package com.example.testingsystem.security;


import com.example.testingsystem.entity.User;
import com.example.testingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с логином '" + username + "' не найден"));

        return UserDetailsImp.build(user);
    }
}

