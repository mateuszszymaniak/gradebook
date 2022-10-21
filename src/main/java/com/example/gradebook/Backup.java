package com.example.gradebook;

import javafx.util.Pair;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Parent;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
}
