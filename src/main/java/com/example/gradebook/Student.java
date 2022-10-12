package com.example.gradebook;

import lombok.Getter;
import lombok.Setter;

public class Student {
    @Getter @Setter private int id;
    @Getter @Setter private String surname, name, studentGroup, schoolYear;

    public Student(int id, String surname, String name, String studentGroup, String schoolYear) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.studentGroup = studentGroup;
        this.schoolYear = schoolYear;
    }
}
