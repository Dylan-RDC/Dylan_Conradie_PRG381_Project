package com.studentportal.webapp.Api_Controllers;


import java.util.*;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;
import com.studentportal.webapp.repo.studentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/student")
public class studentController {
    @Autowired
    studentRepo myRepo;

    @Autowired
    courseRepo cRepo;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public List<student> findStudents()
    {
        try {
            var response = myRepo.findAll(); 
            return response;
            
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/insert")
    public String insertStudent(HttpEntity<student> stud)
    {   
        student newStud = (student)stud.getBody();
        try {
                String password = bCryptPasswordEncoder.encode(newStud.getPassword());
                System.out.println(password.length());
                newStud.setPassword(password);
                myRepo.save(newStud);
                return "Register Successful"; 
        } catch (Exception e) {
            return "Error";
        } 
    }


    @PutMapping("/update")
    public String updateStudent(student stud) {
        try {

            if (myRepo.findById(stud.getStudent_id()).isPresent()) {
                String password = bCryptPasswordEncoder.encode(stud.getPassword());
                stud.setPassword(password);
                myRepo.save(stud);
                return String.format("Updated user: %s Successful", stud.getStudent_name());
            }
            return "USER DOESNT EXIST";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/addModule")
    public String addModule(student stud, course course){
        try {
            Optional<student> myStud = myRepo.findById(stud.getStudent_id());
            if (myStud.isPresent()) {
                student curStud = myStud.get();
                curStud.addStudentCourse(cRepo.findById(course.getCourse_id()).get());
                myRepo.save(curStud);
                return String.format("Updated user: %s Successful", curStud.getStudent_name());
            }
            return "USER DOESNT EXIST";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/testing")
    public List<student> getStudent(){
        
        return findStudents();
    }
  

    @GetMapping("/find/{id}")
    public Optional<student> getStudent(Model model,@PathVariable(value="id") Long id) {
            return myRepo.findById(id);
    }

    @DeleteMapping("/student/{stud_id}/{course_id}")
    public String removeRole(Model model,@PathVariable(value="stud_id") Long stud_id,@PathVariable(value="course_id") Long course_id) {
        try {
            
            Optional<student> myStud = myRepo.findById(stud_id);
            if (myStud.isPresent()) {
                student foundStud = myStud.get();
    
                List<course> courses = foundStud.getStudentCourses();
    
                for (course course : courses) {
                    if (course.getCourse_id()==course_id) {
                        foundStud.getStudentCourses().remove(course);
                        myRepo.save(foundStud);
                        return String.format("Successfully removed %s from student with student id: %d", course.getCourse_name(),foundStud.getStudent_id());
                    }
                }
    
            }
            return "COURSE NOT DELETED";
        } catch (Exception e) {
            return String.format("Failed %s", e.getMessage());
        }


}
}