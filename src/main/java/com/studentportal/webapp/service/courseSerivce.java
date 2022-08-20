package com.studentportal.webapp.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;


@Service
public class courseSerivce {
    
    @Autowired
    courseRepo courseRep;

    public List<course> getAllCourses()
    {
        return courseRep.findAll();
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

}