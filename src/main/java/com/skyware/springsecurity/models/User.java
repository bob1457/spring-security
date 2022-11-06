package com.skyware.springsecurity.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private  String username;

    @Column(nullable = false)
    private  String password;

    @Column(nullable = false)
    private String email;

    public User(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }
}
