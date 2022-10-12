package com.example.gradebook;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DB {
    //private Session session;

//    private String creationString = "CREATE TABLE students (" +
//            "id int(4), " +
//            "name varchar(40), " +
//            "surname varchar(50), " +
//            "averageGrade double(3,2)" +
//            ");";
//    private boolean success = false;

    /*public DB() {
        this.session = HibernateFactory.getSessionFactory().openSession();
    }

    public Session getSession() {
        return this.session;
    }*/

    public static void main() {
//        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
//        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        SessionFactory factory = HibernateFactory.getSessionFactory();
//        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class) .buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        try {
            //User usr = new User(2, "admin", "zaq1@WSX");
            User usr = new User();
            usr.setId(2);
            usr.setLogin("admin1");
            usr.setPassword("zaq1@WSX");
            session.persist(usr);
            t.commit();
            System.out.println("successfully saved");

        }catch (Exception e){
            System.out.println(e);

        }

        factory.close();
        session.close();
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
