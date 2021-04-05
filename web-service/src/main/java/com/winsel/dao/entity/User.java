package com.winsel.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime emailVerifiedAt;
    private String password;
    private String remember_token;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
