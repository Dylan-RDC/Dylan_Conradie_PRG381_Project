package com.studentportal.webapp.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity 
@Table(name= "course", uniqueConstraints =  @UniqueConstraint(columnNames = {"course_name"}))
public class course implements Serializable, Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long course_id;

    @Column(name = "course_name")
    private String course_name;


    
    
    @ManyToMany(mappedBy = "studentcourses",fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    List<student> enrolledstudents = new ArrayList<student>();


    //Constructors
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
// End Constructors
    
    

    @Override
    public String toString() {
        return "course [course_id=" + course_id + ", course_name=" + course_name + ", enrolledstudents="
                + enrolledstudents + "]";
    }

    public course(Long course_id, String course_name) {
        this.course_id = course_id;
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
        List<String> list = new ArrayList<>(Arrays.asList("Test","test"));
    }


    @JsonIgnoreProperties({"studentCourses"})
    public List<student> getEnrolledStudents() {
        return enrolledstudents;
    }

    public void addEnrolledStudent(student newStudent) {
        getEnrolledStudents().add(newStudent);
        newStudent.getStudentCourses().add(this);
    }

    @Override
    public int compareTo(Object o) {
        course c = (course) o;
        return this.getCourse_name().compareTo(c.getCourse_name());
    }   
}