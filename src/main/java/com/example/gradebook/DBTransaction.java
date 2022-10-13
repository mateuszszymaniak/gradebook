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


    // ----------------------
    // User class
    // ----------------------
    public boolean registerUser(String login, String password) {
        if (signIn(login, password)) {
            return false;
        }
        else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                        "users (`id`,`login`,`password`) VALUES (null,?,?)");
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.execute();
            } catch (SQLException e) {
                System.err.println("Error while inserting user data: " + login);
                e.printStackTrace();
                return false;
            }
        }
        return true;
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

    // ----------------------
    // Student class
    // ----------------------
    public boolean addStudent(String surname, String name, String studentGroup, String schoolYear) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "students (`id`,`surname`,`name`,`studentGroup`,`schoolYear`) VALUES (null,?,?,?,?)");
            preparedStatement.setString(1, surname);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, studentGroup);
            preparedStatement.setString(4, schoolYear);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Error while inserting student data: " + surname + " " + name);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Student> getStudents_byId(int studentId) {
        if (studentId==0){
            return getStudents_mechanism("SELECT * FROM students");

        }
        else {
            return getStudents_mechanism("SELECT * FROM students WHERE id=" + studentId);
        }
    }

    public List<Student> getStudents_byName(String name) {
        return getStudents_mechanism("SELECT * FROM students WHERE name LIKE '" + name + "'");
    }

    public List<Student> getStudents_bySurname(String surname) {
        return getStudents_mechanism("SELECT * FROM students WHERE surname LIKE '" + surname + "'");
    }

    public List<Student> getStudents_byGroup(String studentGroup) {
        return getStudents_mechanism("SELECT * FROM students WHERE studentGroup LIKE '" + studentGroup + "'");
    }

    public List<Student> getStudents_bySchoolYear(String schoolYear) {
        return getStudents_mechanism("SELECT * FROM students WHERE schoolYear LIKE '" + schoolYear + "'");
    }

    public void printStudents() {
        List<Student> list = getStudents_mechanism("SELECT * FROM students");
        for (Student stud : list) {
            System.out.println(stud.getId() + " " + stud.getName() + " " + stud.getSurname() + " " + stud.getStudentGroup() + " " + stud.getSchoolYear());
        }
    }

    private List<Student> getStudents_mechanism(String sqlQuery) {
        List<Student> output = new LinkedList<Student>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            int id;
            String surname, name, studentGroup, schoolYear;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                surname = resultSet.getString("surname");
                name = resultSet.getString("name");
                studentGroup = resultSet.getString("studentGroup");
                schoolYear = resultSet.getString("schoolYear");

                output.add(new Student(id, surname, name, studentGroup, schoolYear));
            }
        } catch (SQLException e) {
            System.err.println("Problem with reading data from database");
            e.printStackTrace();
            return null;
        }
        return output;
    }


    // ----------------------
    // Grade class
    // ----------------------
    public boolean addGrade(double grade, String subject, String type, String comment, int studentId, int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "`grades`(`id`, `grade`, `subject`, `type`, `comment`, `studentId`, `userId`) VALUES (null,?,?,?,?,?,?)");
            preparedStatement.setDouble(1, grade);
            preparedStatement.setString(2, subject);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, comment);
            preparedStatement.setInt(5, studentId);
            preparedStatement.setInt(6, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Error while inserting grade data: " + grade + " studentId-" + studentId);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean editGrade(int id, double grade, String subject, String type, String comment, int studentId, int userId) {
        List<Grade> list = getGrades_byId(id);
        if (list.isEmpty()) {
            System.err.println("Grade not found");
            return false;
        }
        else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `grades` SET " +
                        "`grade`=?,`subject`=?,`type`=?,`comment`=?,`studentId`=?,`userId`=? " +
                        "WHERE `id`=" + id);
                preparedStatement.setDouble(1, grade);
                preparedStatement.setString(2, subject);
                preparedStatement.setString(3, type);
                preparedStatement.setString(4, comment);
                preparedStatement.setInt(5, studentId);
                preparedStatement.setInt(6, userId);
                preparedStatement.execute();
            } catch (SQLException e) {
                System.err.println("Error while editing grade data: " + grade + " id-" + id);
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean deleteGrade(int id) {
        List<Grade> list = getGrades_byId(id);
        if (list.isEmpty()) {
            System.err.println("Grade not found");
            return false;
        }
        else {
            try {
                String delete = "DELETE FROM `grades` WHERE `id`=" + id;
                statement.execute(delete);
            } catch (SQLException e) {
                System.err.println("Error while deleting grade data: id-" + id);
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public List<Grade> getGrades_byId (int id) {
        return getGrades_mechanism("SELECT * FROM grades WHERE grades.id=" + id);
    }

    public List<Grade> getGrades_byStudentId (int studentId, String schoolYear) {
        return getGrades_mechanism("SELECT grades.id, grades.grade, grades.subject, grades.type, grades.comment, grades.studentId," +
                "grades.userId FROM grades INNER JOIN students ON grades.studentId = students.id " +
                "WHERE grades.studentId=" + studentId + " AND students.schoolYear LIKE " + schoolYear);
    }

    public List<Grade> getGrades_byUserId (int userId, String schoolYear) {
        return getGrades_mechanism("SELECT grades.id, grades.grade, grades.subject, grades.type, grades.comment, grades.studentId," +
                "grades.userId FROM grades INNER JOIN students ON grades.studentId = students.id " +
                "WHERE grades.userId=" + userId + " AND students.schoolYear LIKE " + schoolYear);
    }

    public List<Grade> getGrades_bySubject (String subject, String schoolYear) {
        return getGrades_mechanism("SELECT grades.id, grades.grade, grades.subject, grades.type, grades.comment, grades.studentId," +
                "grades.userId FROM grades INNER JOIN students ON grades.studentId = students.id " +
                "WHERE grades.subject='" + subject + "' AND students.schoolYear LIKE " + schoolYear);
    }

    public List<Grade> getGrades_byStudentGroup_nSubject (String subject, String studentGroup, String schoolYear) {
        return getGrades_mechanism("SELECT grades.id, grades.grade, grades.subject, grades.type, grades.comment, grades.studentId," +
                "grades.userId FROM grades INNER JOIN students ON grades.studentId = students.id WHERE students.studentGroup LIKE '" + studentGroup + "' " +
                "AND grades.subject LIKE '" + subject + "' AND students.schoolYear LIKE " + schoolYear);
    }

    private List<Grade> getGrades_mechanism(String sqlQuery) {
        List<Grade> output = new LinkedList<Grade>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            int id, studentId, UserId;
            double grade;
            String subject, type, comment;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                grade = resultSet.getDouble("grade");
                subject = resultSet.getString("subject");
                type = resultSet.getString("type");
                comment = resultSet.getString("comment");
                studentId = resultSet.getInt("studentId");
                UserId = resultSet.getInt("UserId");

                output.add(new Grade(id, grade, subject, type, comment, studentId, UserId));
            }
        } catch (SQLException e) {
            System.err.println("Problem with reading data from database");
            e.printStackTrace();
            return null;
        }
        return output;
    }


    // ----------------------

    @Override
    public void finalize() {
        closeConnection();
    }
}
