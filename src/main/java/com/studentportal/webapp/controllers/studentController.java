package com.studentportal.webapp.controllers;


import java.util.*;


import com.studentportal.webapp.models.student;

import com.studentportal.webapp.repo.studentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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



    @GetMapping("/testing")
    public List<student> getStudent(){

        return findStudents();
    }
  
}