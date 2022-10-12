package com.example.gradebook;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private String password;

//    public User() {
//    }
//
//    public User(int id, String login, String password) {
//        this.id = id;
//        this.login = login;
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id = " + id +
//                ", login = " + login +
//                ", password = " + password +
//                "}";
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    // SELECT id FROM `users` WHERE login LIKE :login AND password LIKE :password
    public void signIn(DB db, String login, String password) {
        setLogin(login);
        setPassword(password);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            String encPasswd = new String(bytes);
            password = encPasswd;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Session session = db.getSession();
//        Transaction tx = session.beginTransaction();
        //Query query = session.createQuery("SELECT id FROM `users` WHERE login LIKE :login AND password LIKE :password").setParameter("login", login).setParameter("password", password);
        //Query query = session.createQuery("FROM users");
        try {
            Query<User> query1 = session.createQuery("FROM User", User.class);
            query1.list();
//            tx.commit();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
     */
}