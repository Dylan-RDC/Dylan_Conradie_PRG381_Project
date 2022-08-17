package com.studentportal.webapp.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity 
@Table(name= "course")
public class course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long course_id;

    @Column(name = "course_name")
    private String course_name;

    

    @ManyToMany(mappedBy = "studentcourses")
    List<student> enrolledstudents = new ArrayList<student>();

    public course() {
    }

    public course(Long course_id) {
        this.course_id = course_id;
    }

    public course(Long course_id, String course_name, List<student> enrolledStudents) {
        this.course_id = course_id;
        this.course_name = course_name;
        enrolledstudents = enrolledStudents;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }


    @JsonIgnoreProperties({"studentCourses"})
    public List<student> getEnrolledStudents() {
        return enrolledstudents;
    }

    public void addEnrolledStudent(student newStudent) {
        getEnrolledStudents().add(newStudent);
        newStudent.getStudentCourses().add(this);
    }   
}