package com.example.gradebook;

import javafx.util.Pair;
import org.jasypt.util.text.StrongTextEncryptor;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Backup extends DBTransaction{
    private final List<User> users;
    private final List<Student> students;
    private final List<Grade> grades;

    public Backup() {
        this.users = getUsers_mechanism("SELECT * FROM users");
        this.students = getStudents_mechanism("SELECT * FROM students");
        this.grades = getGrades_mechanism("SELECT * FROM grades");
    }



    // ----------------------
    // Export backup
    // ----------------------

    public void createBackup(String key) {
        Document doc = xmlBegin();

        Element gradebook = new Element("gradebook");
        Element user = new Element("users");
        Element student = new Element("students");
        Element grade = new Element("grades");

        for (User item : users) {
            Element userElement = userToXML(item, key);
            user.addContent(userElement);
        }

        for (Student item : students) {
            Element studentElement = studentToXML(item, key);
            student.addContent(studentElement);
        }

        for (Grade item : grades) {
            Element gradeElement = gradeToXML(item, key);
            grade.addContent(gradeElement);
        }

        gradebook.addContent(user);
        gradebook.addContent(student);
        gradebook.addContent(grade);

        doc.getRootElement().addContent(gradebook);
        saveXML(doc);
    }

    public Element userToXML(User usr, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);

        Element userElement = new Element("user");
        userElement.setAttribute(new Attribute("id", Integer.toString(usr.getId())));

        Element login = new Element("login");
        login.setText(textEncryptor.encrypt(usr.getLogin()));
        Element password = new Element("password");
        password.setText(textEncryptor.encrypt(usr.getPassword()));

        userElement.addContent(login);
        userElement.addContent(password);

        return userElement;
    }

    public Element studentToXML(Student stud, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);

        Element studentElement = new Element("student");
        studentElement.setAttribute(new Attribute("id", Integer.toString(stud.getId())));

        Element surname = new Element("surname");
        surname.setText(textEncryptor.encrypt(stud.getSurname()));
        Element name = new Element("name");
        name.setText(textEncryptor.encrypt(stud.getName()));
        Element studentGroup = new Element("studentGroup");
        studentGroup.setText(textEncryptor.encrypt(stud.getStudentGroup()));
        Element schoolYear = new Element("schoolYear");
        schoolYear.setText(textEncryptor.encrypt(stud.getSchoolYear()));

        studentElement.addContent(surname);
        studentElement.addContent(name);
        studentElement.addContent(studentGroup);
        studentElement.addContent(schoolYear);

        return studentElement;
    }

    public Element gradeToXML(Grade gr, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);

        Element gradeElement = new Element("grade");
        gradeElement.setAttribute(new Attribute("id", Integer.toString(gr.getId())));

        Element gradeVal = new Element("grade");
        gradeVal.setText(textEncryptor.encrypt(Double.toString(gr.getGrade())));
        Element subject = new Element("subject");
        subject.setText(textEncryptor.encrypt(gr.getSubject()));
        Element type = new Element("type");
        type.setText(textEncryptor.encrypt(gr.getType()));
        Element comment = new Element("comment");
        comment.setText(textEncryptor.encrypt(gr.getComment()));
        Element studentId = new Element("studentId");
        studentId.setText(textEncryptor.encrypt(Integer.toString(gr.getStudentId())));
        Element userId = new Element("userId");
        userId.setText(textEncryptor.encrypt(Integer.toString(gr.getUserId())));

        gradeElement.addContent(gradeVal);
        gradeElement.addContent(subject);
        gradeElement.addContent(type);
        gradeElement.addContent(comment);
        gradeElement.addContent(studentId);
        gradeElement.addContent(userId);

        return gradeElement;
    }

    private Document xmlBegin() {
        Element xml = new Element("xml");
        Document doc = new Document(xml);
        return doc;
    }

    private void saveXML(Document doc) {
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            File file = new File("backup.xml");
            FileOutputStream out = new FileOutputStream( file, false);

            xmlOutput.output(doc, out);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    // ----------------------
    // Import backup
    // ----------------------

    public boolean importBackup(String key) {
        if (initImport()) {
            Element xml = xmlReadBegin();
            if (xml != null) {
                Element gradebook = xml.getChild("gradebook");
                if (!processUser(gradebook.getChildren("users"), key)) return false;
                if (!processStudent(gradebook.getChildren("students"), key)) return false;
                if (!processGrade(gradebook.getChildren("grades"), key)) return false;
            }
        }
        return true;
    }

    private boolean initImport() {
        try {
            for (int i=(tables.length-1); i>=0;i--) {
                String init = "DELETE from " + tables[i];
                statement.execute(init);
                init = "ALTER TABLE " + tables[i] + " AUTO_INCREMENT=1";
                statement.execute(init);
            }
        } catch (SQLException e) {
            System.err.println("Import init error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Element xmlReadBegin() {
        File input = new File("backup.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(input);
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }

        return doc.getRootElement();
    }

    private boolean processUser(List<Element> usersList, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);

        String login, password;
        List<Element> users = usersList.get(0).getChildren("user");
        for (int i=0; i<users.size();i++) {
            Element user = users.get(i);
            login = textEncryptor.decrypt(user.getChildText("login"));
            password = textEncryptor.decrypt(user.getChildText("password"));
            if(!reRegisterUser(login, password)) {
                return false;
            }
        }
        return true;
    }

    private boolean processStudent(List<Element> studentsList, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);

        String surname, name, studentGroup, schoolYear;
        List<Element> students = studentsList.get(0).getChildren("student");
        for (int i=0;i<students.size();i++) {
            Element student = students.get(i);
            surname = textEncryptor.decrypt(student.getChildText("surname"));
            name = textEncryptor.decrypt(student.getChildText("name"));
            studentGroup = textEncryptor.decrypt(student.getChildText("studentGroup"));
            schoolYear = textEncryptor.decrypt(student.getChildText("schoolYear"));
            if(!addStudent(surname, name, studentGroup, schoolYear)) {
                return false;
            }
        }
        return true;
    }

    private boolean processGrade(List<Element> gradesList, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);

        double grade;
        String subject, type, comment;
        int studentId, userId;
        List<Element> grades = gradesList.get(0).getChildren("grade");
        for (int i=0;i<grades.size();i++) {
            Element gr = grades.get(i);
            grade = Double.parseDouble(textEncryptor.decrypt(gr.getChildText("grade")));
            subject = textEncryptor.decrypt(gr.getChildText("subject"));
            type = textEncryptor.decrypt(gr.getChildText("type"));
            comment = textEncryptor.decrypt(gr.getChildText("comment"));
            studentId = Integer.parseInt(textEncryptor.decrypt(gr.getChildText("studentId")));
            userId = Integer.parseInt(textEncryptor.decrypt(gr.getChildText("userId")));
            if(!addGrade(grade, subject, type, comment, studentId, userId)) {
                return false;
            }
        }
        return true;
    }
}
