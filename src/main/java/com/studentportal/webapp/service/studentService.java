package com.studentportal.webapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;
import com.studentportal.webapp.repo.studentRepo;

@Service
@Transactional
public class studentService {

    @Autowired
    courseRepo cRepo;
    
    @Autowired
    studentRepo studRepo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public List<student> getAllStudents()
    {
        return studRepo.findAll();
    }

    public String addStudent(student newStud) {

        try {
            newStud.setPassword(encoder.encode(newStud.getPassword()));
            newStud = studRepo.save(newStud);
    
            return String.format("New student has been added into Database, student number: %d",newStud.getStudent_id() );
        } catch (Exception e) {
            return "FAILED";
        }

    }

    public String deleteStudent(Long stud_id)
    {
        try {
            studRepo.deleteById(stud_id);
            return String.format("Student with ID: %s was deleted successfully", stud_id);
            
        } catch (Exception e) {
            return "FAILED";
        }
    }

    public String updateStudent(student stud)
    {
        try {
            
            studRepo.save(stud);
            return String.format("Student with ID: %s was updated successfully", stud.getStudent_id());
        } catch (Exception e) {

            return "FAILED";
        }
    }

    public student getStudent(Long stud_id)
    {
        try {
            return studRepo.getReferenceById(stud_id);
            
        } catch (Exception e) {
             return null;
        }
    }

    public student getStudentByEmail(String email)
    {

        try {
            return studRepo.getStudentByEmail(email);
            
        } catch (Exception e) {
            return null;
        }
    }

    public List<course> getStudentCourses(Long student_id )
    {
        try {
            List<course> studList = studRepo.getStudentCourses(student_id);
            Collections.sort(studList);
            return studList;
            
        } catch (Exception e) {
            return null;
        }


    }



    public boolean addStudentCourse(Long stud_id,Long course_id)
    {
        try {
            student stud = studRepo.getReferenceById(stud_id);
            course nC = cRepo.getReferenceById(course_id);
            if (nC!=null) {
                stud.addStudentCourse(nC);
                studRepo.save(stud);
                
            } 
            return true;
        } catch (Exception e) {
            return false;
        }
       
    }

    public boolean removeStudentCourse(Long stud_id,Long course_id)
    {
        try {
            student stud = studRepo.getReferenceById(stud_id);
            List<course> courses = stud.getStudentCourses();
            List<course> newCourses = new ArrayList<>();
            for (course c : courses) {
                if (c.getCourse_id()!=course_id) {
                    newCourses.add(c);
                }
            }
            stud.setStudCourse(newCourses);
            studRepo.save(stud);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
