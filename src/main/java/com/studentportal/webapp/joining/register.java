package com.studentportal.webapp.joining;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Embeddable;

@Embeddable
public class register implements Serializable{
    @Column(name="student_id")
    Long student_id;

    @Column(name="course_id")
    Long course_id;


    

    public register() {
    }

        
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
        result = prime * result + ((student_id == null) ? 0 : student_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        register other = (register) obj;
        if (course_id == null) {
            if (other.course_id != null)
                return false;
        } else if (!course_id.equals(other.course_id))
            return false;
        if (student_id == null) {
            if (other.student_id != null)
                return false;
        } else if (!student_id.equals(other.student_id))
            return false;
        return true;
    }




    public register(Long student_id, Long course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }



    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    

}
