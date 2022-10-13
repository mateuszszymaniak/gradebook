package com.example.gradebook;

import lombok.Getter;
import lombok.Setter;

public class Grade {
    @Getter @Setter private int id, studentId, UserId;
    @Getter @Setter private double grade;
    @Getter @Setter private String subject, type, comment;

    public Grade(int id, double grade, String subject, String type, String comment, int studentId, int UserId) {
        this.id = id;
        this.grade = grade;
        this.subject = subject;
        this.type = type;
        this.comment = comment;
        this.studentId = studentId;
        this.UserId = UserId;
    }
}
