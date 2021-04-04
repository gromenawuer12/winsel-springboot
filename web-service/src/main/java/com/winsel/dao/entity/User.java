package com.winsel.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter private Integer id;
    @Getter @Setter private String name;
    @Getter @Setter private String email;
    @Getter @Setter private LocalDateTime emailVerifiedAt;
    @Getter @Setter private String password;
    @Getter @Setter private String remember_token;
    @Getter @Setter private LocalDateTime created_at;
    @Getter @Setter private LocalDateTime updated_at;
}
