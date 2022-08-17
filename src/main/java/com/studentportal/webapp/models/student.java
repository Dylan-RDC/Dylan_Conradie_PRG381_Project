package com.studentportal.webapp.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "student")
public class student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long student_id;

    @Column(name = "student_name")
    private String student_name;

    @Column(name = "student_address")
    private String student_address;

    @Column(name = "student_email")
    private String contact;

    @Column(name = "student_password")
    private String student_password;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "register",
    joinColumns = {@JoinColumn(name="student_id")},
    inverseJoinColumns = {@JoinColumn(name="course_id")}
    )
     Set<course> StudentCourses = new HashSet<>();

    public student() {
    }

    public student(Long student_id, String student_name, String student_address, String contact,
        String student_password) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_address = student_address;
        this.contact = contact;
        this.student_password = student_password;
    }

    public student(String student_name, String student_address, String contact, String student_password) {
        this.student_name = student_name;
        this.student_address = student_address;
        this.contact = contact;
        this.student_password = student_password;
    }


    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    @JsonBackReference
    public Set<course> getStudentCourses() {
        return StudentCourses;
    }

    public void addStudentCourse(course newCourse) {
        StudentCourses.add(newCourse);
        newCourse.getEnrolledStudents().add(this);
    }

    

    

    
    
    
    





    

}
