package com.studentportal.webapp.Student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unit") //provides the main overarching / in the url so /unit/ sub 
public class StudentController {

    final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/testing")
    public List<student> getStudents()
    {
        return studentService.getStudents();
    }


}
