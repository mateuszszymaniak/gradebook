package com.example.gradebook;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter private int id;
    @Getter @Setter private String login, password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}
