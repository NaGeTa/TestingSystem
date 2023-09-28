package com.example.testingsystem.security;

import com.example.testingsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserDetailsImp implements UserDetails {

    private int id;
    private String first_name;
    private String last_name;
    private String login;
    private String email;
    private String password;
    private Date date_of_birth;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetailsImp build(User user) {
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new UserDetailsImp(user.getId(), user.getFirst_name(), user.getLast_name(), user.getLogin(),
                user.getEmail(), user.getPassword(), user.getDate_of_birth(), authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
