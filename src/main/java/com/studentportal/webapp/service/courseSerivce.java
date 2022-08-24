package com.studentportal.webapp.service;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;


@Service
@Transactional
public class courseSerivce {
    
    @Autowired
    courseRepo courseRep;

    public List<course> getAllCourses()
    {
        return courseRep.findAll();
    }

    public String addCourse(course newCourse) {

        try {
            
            newCourse = courseRep.save(newCourse);
    
            return String.format("New student has been added into Database, student number: %d",newCourse.getCourse_id() );
        } catch (Exception e) {
            return "FAILED";
        }

    } 

    public List<student> getCourseStudents(Long course_id )
    {
        List<student> studList = courseRep.getCourseStudents(course_id);
        return studList;
    }

    public course getCourseByID(Long course_id)
    {
        return courseRep.getReferenceById(course_id);
    }

    public String deleteCourseByID(Long course_id)
    {
        try {
            courseRep.deleteById(course_id);
            return "Successfully deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}