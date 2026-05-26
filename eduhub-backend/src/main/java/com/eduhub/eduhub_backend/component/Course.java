package com.eduhub.eduhub_backend.component;

public class Course {
    int courseCode;
    String courseName;
    int credit;

    public Course(int courseCode, String courseName, int credit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
    }
}
