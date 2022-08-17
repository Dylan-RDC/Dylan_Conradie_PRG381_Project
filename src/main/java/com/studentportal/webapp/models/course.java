package com.studentportal.webapp.models;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity 
@Table(name= "course")
public class course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long course_id;

    @Column(name = "course_name")
    private String course_name;

    @ManyToMany(mappedBy = "StudentCourses")
    @JsonIgnore
    Set<student> EnrolledStudents = new HashSet<>();

    public course() {
    }

    public course(Long course_id, String course_name) {
        this.course_id = course_id;
        this.course_name = course_name;
    }

    public course(String course_name) {
        this.course_name = course_name;
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

    @JsonBackReference//prevents infinite recusrion
    public Set<student> getEnrolledStudents() {
        return EnrolledStudents;
    }

    public void addEnrolledStudent(student newStudent) {
        EnrolledStudents.add(newStudent);
        newStudent.getStudentCourses().add(this);
    }
    
    

       
}