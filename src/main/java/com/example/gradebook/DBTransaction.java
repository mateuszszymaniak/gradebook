package com.example.gradebook;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBTransaction extends DB {
    public DBTransaction() {
        /*
        insertData("users", "usr1", "qwerty");
        insertData("users", "usr2", "zaq1@WSX");
         */
    }

    public void registerUser(String login, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "users (`id`,`login`,`password`) VALUES (null,?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Error while inserting user data: " + login);
            e.printStackTrace();
        }
    }

    public void showUsers() {
        List<User> list = getData("users");

        for (User usr : list) {
            System.out.println(usr.getId() + " " + usr.getLogin() + " " + usr.getPassword());
        }
    }

    @Override
    public void finalize() {
        closeConnection();
    }
}
