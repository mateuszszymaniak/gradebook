package com.example.gradebook;

import org.hibernate.Session;

public class DB {
    private Session session;

//    private String creationString = "CREATE TABLE students (" +
//            "id int(4), " +
//            "name varchar(40), " +
//            "surname varchar(50), " +
//            "averageGrade double(3,2)" +
//            ");";
//    private boolean success = false;

    public DB() {
        this.session = HibernateFactory.getSessionFactory().openSession();
    }

    public Session getSession() {
        return this.session;
    }

    /*
    public boolean createTables() {
        Transaction tx;
//        Query query;
//        try {
//            tx = session.beginTransaction();
//             query = session.createQuery("SELECT TABLE_NAME " +
//                    "FROM INFORMATION_SCHEMA.TABLES " +
//                    "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='gradebook'");
//        }
//        catch (Exception e) {
//            System.out.println("Database not found.\nError code: " + e.toString());
//            return false;
//        }
//        tx.commit();
        //if (query.list().isEmpty()) {
            try {
                tx = session.beginTransaction();
                Query creation = session.createQuery(this.creationString);
                //creation.uniqueResult();
            }
            catch (Exception e) {
                System.out.println("Tables creation failed.\nError code: " + e.toString());
                return false;
            }
            tx.commit();
        //}

        return true;
    }
     */
}
