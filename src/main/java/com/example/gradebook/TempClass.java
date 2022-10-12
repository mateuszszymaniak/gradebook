package com.example.gradebook;

import java.util.List;

public class TempClass {
    public static void main() {

        // User class

        DBTransaction tx = new DBTransaction();
        if (tx.registerUser("admin", "1234567p")) System.out.println("Signed up");   // register new user, return true or false
        else System.out.println("This login is existing in db");
        tx.printUsers(0); // show all users (id, login, password)   // userId=0 to show all users, type specified userId to get one user with this ID
        if (tx.signIn("usr1", "qwerty")) {  // sign in method; return true or false
            System.out.println("Signed in");
        } else {
            System.out.println("Incorrect pass");
        }

        //Student class
        tx.addStudent("Kowalski", "Jan", "4c", "2022/2023");    // add student to the db, return true or false
        tx.printStudents(); // show all students (id, name, surname, studentGroup, schoolYear)
    }
}
