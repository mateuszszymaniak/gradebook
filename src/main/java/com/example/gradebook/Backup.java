package com.example.gradebook;

import javafx.util.Pair;
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

    public void createBackup() {
        Document doc = xmlBegin();

        Element gradebook = new Element("gradebook");
        Element user = new Element("users");
        Element student = new Element("students");
        Element grade = new Element("grades");

        for (User item : users) {
            Element userElement = userToXML(item);
            user.addContent(userElement);
        }

        for (Student item : students) {
            Element studentElement = studentToXML(item);
            student.addContent(studentElement);
        }

        for (Grade item : grades) {
            Element gradeElement = gradeToXML(item);
            grade.addContent(gradeElement);
        }

        gradebook.addContent(user);
        gradebook.addContent(student);
        gradebook.addContent(grade);

        doc.getRootElement().addContent(gradebook);
        saveXML(doc);
    }

    public Element userToXML(User usr) {
            Element userElement = new Element("user");
            userElement.setAttribute(new Attribute("id", Integer.toString(usr.getId())));

            Element login = new Element("login");
            login.setText(usr.getLogin());
            Element password = new Element("password");
            password.setText(usr.getPassword());

            userElement.addContent(login);
            userElement.addContent(password);

            return userElement;
    }

    public Element studentToXML(Student stud) {
        Element studentElement = new Element("student");
        studentElement.setAttribute(new Attribute("id", Integer.toString(stud.getId())));

        Element surname = new Element("surname");
        surname.setText(stud.getSurname());
        Element name = new Element("name");
        name.setText(stud.getName());
        Element studentGroup = new Element("studentGroup");
        studentGroup.setText(stud.getStudentGroup());
        Element schoolYear = new Element("schoolYear");
        schoolYear.setText(stud.getSchoolYear());

        studentElement.addContent(surname);
        studentElement.addContent(name);
        studentElement.addContent(studentGroup);
        studentElement.addContent(schoolYear);

        return studentElement;
    }

    public Element gradeToXML(Grade gr) {
        Element gradeElement = new Element("grade");
        gradeElement.setAttribute(new Attribute("id", Integer.toString(gr.getId())));

        Element gradeVal = new Element("grade");
        gradeVal.setText(Double.toString(gr.getGrade()));
        Element subject = new Element("subject");
        subject.setText(gr.getSubject());
        Element type = new Element("type");
        type.setText(gr.getType());
        Element comment = new Element("comment");
        comment.setText(gr.getComment());
        Element studentId = new Element("studentId");
        studentId.setText(Integer.toString(gr.getStudentId()));
        Element userId = new Element("userId");
        userId.setText(Integer.toString(gr.getUserId()));

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

    public boolean importBackup() {
        if (initImport()) {
            Element xml = xmlReadBegin();
            if (xml != null) {
                Element gradebook = xml.getChild("gradebook");
                if (!processUser(gradebook.getChildren("users"))) return false;
                if (!processStudent(gradebook.getChildren("students"))) return false;
                if (!processGrade(gradebook.getChildren("grades"))) return false;
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

    private boolean processUser(List<Element> usersList) {
        String login, password;
        List<Element> users = usersList.get(0).getChildren("user");
        for (int i=0; i<users.size();i++) {
            Element user = users.get(i);
            login = user.getChildText("login");
            password = user.getChildText("password");
            if(!registerUser(login, password)) {
                return false;
            }
        }
        return true;
    }

    private boolean processStudent(List<Element> studentsList) {
        String surname, name, studentGroup, schoolYear;
        List<Element> students = studentsList.get(0).getChildren("student");
        for (int i=0;i<students.size();i++) {
            Element student = students.get(i);
            surname = student.getChildText("surname");
            name = student.getChildText("name");
            studentGroup = student.getChildText("studentGroup");
            schoolYear = student.getChildText("schoolYear");
            if(!addStudent(surname, name, studentGroup, schoolYear)) {
                return false;
            }
        }
        return true;
    }

    private boolean processGrade(List<Element> gradesList) {
        double grade;
        String subject, type, comment;
        int studentId, userId;
        List<Element> grades = gradesList.get(0).getChildren("grade");
        for (int i=0;i<grades.size();i++) {
            Element gr = grades.get(i);
            grade = Double.parseDouble(gr.getChildText("grade"));
            subject = gr.getChildText("subject");
            type = gr.getChildText("type");
            comment = gr.getChildText("comment");
            studentId = Integer.parseInt(gr.getChildText("studentId"));
            userId = Integer.parseInt(gr.getChildText("userId"));
            if(!addGrade(grade, subject, type, comment, studentId, userId)) {
                return false;
            }
        }
        return true;
    }
}
