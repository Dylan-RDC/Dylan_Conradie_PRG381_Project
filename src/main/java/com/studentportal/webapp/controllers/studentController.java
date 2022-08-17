package com.studentportal.webapp.controllers;


import java.util.*;


import com.studentportal.webapp.models.student;

import com.studentportal.webapp.repo.studentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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


    @PutMapping("update")
    public String updateStudent(student stud) {
        
        try {
            myRepo.save(stud);
            return "Register Successful";
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