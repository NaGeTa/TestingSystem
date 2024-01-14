package com.example.testingsystem.config;

import com.example.testingsystem.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    UserDetailsServiceImpl userDetailsService;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/passwordReset/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form.loginPage("/login")
                        .defaultSuccessUrl("/menu")
                        .permitAll())
                .logout((logout) -> logout.permitAll());


        return http.build();
    }

    @Bean
    public LogoutHandler myLogoutHandler(){
        return new MyLogoutHandler();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
