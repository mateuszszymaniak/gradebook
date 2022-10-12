package com.example.gradebook;

import java.util.List;

public class TempClass {
    public static void main() {
        DBTransaction tx = new DBTransaction();
        tx.registerUser("admin", "1234567p");   // how to register new user
        tx.printUsers(0); // show all users (id, login, password)   // userId=0 to show all users, type specified userId to get one user with this ID
    }
}
