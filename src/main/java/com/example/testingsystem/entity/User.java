package com.example.testingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 40, message = "Длина имени должна быть от 2 до 40 символов")
    private String first_name;

    @Column(name = "last_name")
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 40, message = "Длина фамилии должна быть от 2 до 40 символов")
    private String last_name;

    @Column(name = "login")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @Column(name = "email")
    @NotBlank(message = "Почта не может быть пустой")
    @Email(message = "Некорректная почта")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Введите пароль")
    private String password;

    @Column(name = "year_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Past(message = "Эта дата еще на настала")
    @NotNull(message = "Укажите дату рождения")
    private Date date_of_birth;

    @Column(name = "date_of_registration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_of_registration = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "do_send", columnDefinition = "default 'false'")
    private boolean doSend = true;

    public boolean isBlocked(){
        return role == Role.BLOCKED;
    }
}
