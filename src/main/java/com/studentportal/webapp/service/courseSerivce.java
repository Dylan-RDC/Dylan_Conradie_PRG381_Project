package com.studentportal.webapp.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Autowired;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;


@Service
@Transactional
public class courseSerivce {
    
    @Autowired
    courseRepo courseRep;

    

    public List<course> filteredCourses(student CurrentStud)
    {   
        List<course> courses = courseRep.findAll();
        List<course> filtered = new ArrayList<>();

        for (course c : courses) {
            boolean found = false;
            for (course studc : CurrentStud.getStudentCourses()) {
                if (c.getCourse_id()==studc.getCourse_id()) {
                    found = true;
                    break;
                }

            }
            if (!found) {
                filtered.add(c);
            } 


        }
        return filtered;
    }

    public course getByID(Long course_id)
    {
        course found;
        if (courseRep.existsById(course_id)) {
            found = courseRep.getReferenceById(course_id);
            return found;
        }
        return null;
    }

    public List<course> getAllCourses()
    {
        List<course> courses = courseRep.findAll();
        Collections.sort(courses);
        return courses;
    }

    public String addCourse(course newCourse) {

        try {
            
            if (courseRep.getCourseByName(newCourse.getCourse_name())!=null) {
                return "Course Name already in use";
            }

            else
            {

                newCourse = courseRep.save(newCourse);
        
                return String.format("New Course has been added into Database, student number: %d",newCourse.getCourse_id() );
            }

        } catch (Exception e) {
            System.out.println("FAILED");
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