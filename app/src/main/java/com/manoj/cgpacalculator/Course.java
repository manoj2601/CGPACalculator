package com.manoj.cgpacalculator;

public class Course {
    private String courseName;
    private int grade;
    private int credit;

    public Course(String courseName, String grade, String credit) {
        this.courseName = courseName;
        this.grade = Integer.parseInt(grade);
        this.credit = Integer.parseInt(credit);
    }

    public String getCourseName(){
        return this.courseName;
    }
    public int getCredit() {
        return this.credit;
    }
    public int getGrade() {
        return this.grade;
    }
}
