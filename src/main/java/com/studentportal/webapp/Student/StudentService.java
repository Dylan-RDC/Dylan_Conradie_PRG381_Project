package com.studentportal.webapp.Student;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class StudentService {
    
    public List<student> getStudents()
    {
        return List.of(
            new student(1l,
                       "Frikkie",
                       "Email.gmail.com",
                       "name",
                       "21" 
            )
        );
    }

}
