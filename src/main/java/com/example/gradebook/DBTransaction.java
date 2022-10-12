package com.example.gradebook;

import java.util.List;

public class DBTransaction extends DB {
    public DBTransaction() {
        insertData("users", "usr1", "qwerty");
        insertData("users", "usr2", "zaq1@WSX");

        List<User> list = getData("users");

        for (User usr : list) {
            System.out.println(usr.getId() + " " + usr.getLogin() + " " + usr.getPassword());
        }
        closeConnection();
    }
}
