package com.studentportal.webapp.Api_Controllers;

import java.util.*;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.courseRepo;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class courseController {
    @Autowired
    courseRepo myRepo;

    public List<course> findCourses()
    {
        try {
            var response = myRepo.findAll(); 
            List<course> filtered = new ArrayList<>();
            
            response.forEach((c)->{
                
                course fixed_course = new course(c.getCourse_id(), c.getCourse_name());

                List<student> studs = new ArrayList<>();
                for (student stud : c.getEnrolledStudents()) {//hides student passwords
                    studs.add(new student(stud.getStudent_id(),stud.getStudent_name(),stud.getEmail()));
                }

                filtered.add(new course(c.getCourse_id(), c.getCourse_name(), studs));

            });

            return filtered;
            
        } catch (Exception e) {
             System.out.println(e.getMessage());
            return null;
        }
    }

    public List<course> findAllCourses()
    {
        try {
            var response = myRepo.findAll(); 
            // System.out.println(response.get(0).getCourse_id()+response.get(0).getEnrolledStudents().toString());
            return response;
            
        } catch (Exception e) {
             System.out.println(e.getMessage());
            return null;
        }
    }

    
    @GetMapping("/testing")
    public List<course> getCourses(){

        return findCourses();
    }
  

    @GetMapping("/find/{id}")
    public Optional<course> getAttr(Model model,@PathVariable(value="id") Long id) {
            return myRepo.findById(id);
    }


}
