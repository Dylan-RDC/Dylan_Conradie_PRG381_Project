package com.studentportal.webapp.controllers;

import java.util.*;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;
import com.studentportal.webapp.repo.studentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class courseController {
    @Autowired
    courseRepo myRepo;

    public List<course> findCourse()
    {
        try {
            var response = myRepo.findAll(); 
            System.out.println(response.get(0).getCourse_id()+response.get(0).getEnrolledStudents().toString());


            return response;
            
        } catch (Exception e) {
             System.out.println(e.getMessage());
            return null;
        }
    }

    
    @GetMapping("/testing")
    public List<course> getStudent(){

        return findCourse();
    }
  

    @GetMapping("/find/{id}")
    public Optional<course> getAttr(Model model,@PathVariable(value="id") Long id) {
            return myRepo.findById(id);
    }


}
