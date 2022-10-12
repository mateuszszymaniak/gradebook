package com.example.gradebook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
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

    public boolean signIn(String login, String password) {
        List<User> list = getUsers_mechanism("SELECT * FROM users WHERE login LIKE '" + login + "' AND password LIKE '" + password + "'");
        if(list.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    private List<User> getUsers(int id) {
        if (id==0){
            return getUsers_mechanism("SELECT * FROM users");

        }
        else {
            return getUsers_mechanism("SELECT * FROM users WHERE id=" + id);
        }
    }

    private List<User> getUsers_mechanism(String sqlQuery) {
        List<User> output = new LinkedList<User>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            int id;
            String login, password;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                login = resultSet.getString("login");
                password = resultSet.getString("password");

                output.add(new User(id, login, password));
            }
        } catch (SQLException e) {
            System.err.println("Problem with reading data from database");
            e.printStackTrace();
            return null;
        }
        return output;
    }

    public void printUsers(int userId) {
        List<User> list = getUsers(userId);
        for (User usr : list) {
            System.out.println(usr.getId() + " " + usr.getLogin() + " " + usr.getPassword());
        }
    }

    @Override
    public void finalize() {
        closeConnection();
    }
}
