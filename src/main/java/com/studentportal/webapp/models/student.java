package com.studentportal.webapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "student")
public class student implements Serializable, Iuser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long student_id;

    @Column(name = "student_name")
    private String student_name;

    @Column(name = "student_address")
    private String student_address;

    @Column(name = "student_email")
    private String student_email;

    @Column(name = "student_password")
    private String student_password;

    
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(name = "register",
    joinColumns = {@JoinColumn(name="student_id")},
    inverseJoinColumns = {@JoinColumn(name="course_id")}
    )
     List<course> studentcourses = new ArrayList<course>();

    public student() {
    }

    public student(Long student_id, String student_name, String student_address, String student_email,
        String student_password) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_address = student_address;
        this.student_email = student_email;
        this.student_password = student_password;
    }

    public student(String student_name, String student_address, String student_email, String student_password) {
        this.student_name = student_name;
        this.student_address = student_address;
        this.student_email = student_email;
        this.student_password = student_password;
    }


    public void setStudent_id(Long student_ID) {
        this.student_id = student_ID;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    public String getstudent_email() {
        return student_email;
    }

    public void setstudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    
    @JsonIgnoreProperties({"enrolledStudents"})
    public List<course> getStudentCourses() {
        return studentcourses;
    }

    public void addStudentCourse(course newCourse) {
        studentcourses.add(newCourse);
        newCourse.getEnrolledStudents().add(this);
    }

    

    @Override
    public String toString() {
        return "student [student_email=" + student_email + ", student_address=" + student_address + ", student_id=" + student_id
                + ", student_name=" + student_name + ", student_password=" + student_password + ", studentcourses="
                + studentcourses + "]";
    }

    public Long getStudent_id() {
        return student_id;
    }

    @Override
    public String getEmail() {
        
        return student_email;
    }

    @Override
    public String getPassword() {
        
        return this.student_password;
    }
}
