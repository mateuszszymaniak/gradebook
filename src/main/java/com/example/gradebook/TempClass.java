package com.example.gradebook;

import java.util.List;

public class TempClass {
    public static void main() {
        DBTransaction tx = new DBTransaction();
        tx.registerUser("admin", "1234567p");   // how to register new user
        tx.showUsers(); // show all users (id, login, password)
    }
}
