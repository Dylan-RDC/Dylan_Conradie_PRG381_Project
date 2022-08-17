package com.studentportal.webapp.controllers;


import java.security.SecureRandom;
import java.util.*;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;
import com.studentportal.webapp.repo.studentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/student")
public class studentController {
    @Autowired
    studentRepo myRepo;

    @Autowired
    courseRepo cRepo;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10,new SecureRandom());

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
    public String insertStudent(student stud)
    {
        try {
            if (stud.getStudent_id()==null) {
                String password = bCryptPasswordEncoder.encode(stud.getPassword());
                System.out.println(password.length());
                stud.setStudent_password(password);
                myRepo.save(stud);
                return "Register Successful"; 
            }
            else
            {
                return "User already exists";
            }
        } catch (Exception e) {
            return e.getMessage();
        } 
    }


    @PutMapping("/update")
    public String updateStudent(student stud) {
        try {

            if (myRepo.findById(stud.getStudent_id()).isPresent()) {
                String password = bCryptPasswordEncoder.encode(stud.getPassword());
                stud.setStudent_password(password);
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

}