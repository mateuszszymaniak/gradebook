package com.example.gradebook;

import java.util.List;

public class TempClass {
    public static void main() {
        DB db = new DB();

        db.insertData("users", "usr1", "qwerty");
        db.insertData("users", "usr2", "zaq1@WSX");

        List<User> list = db.getData("users");

        for (User usr : list) {
            System.out.println(usr.getId() + " " + usr.getLogin() + " " + usr.getPassword());
        }
        db.closeConnetion();
    }
}
