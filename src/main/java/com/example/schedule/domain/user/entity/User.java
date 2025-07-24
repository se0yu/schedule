package com.example.schedule.domain.user.entity;

import com.example.schedule.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String username, String email, String password){
        if (username != null) this.username = username;
        if (email != null) this.email = email;
        if (password != null) this.password = password;
    }
}