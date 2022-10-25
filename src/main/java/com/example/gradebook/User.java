package com.example.gradebook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class User {
    @Getter @Setter private int id;
    @Getter @Setter private String login, password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
    //public User(){}
}
