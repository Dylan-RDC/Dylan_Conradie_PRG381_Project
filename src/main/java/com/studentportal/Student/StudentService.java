package com.studentportal.Student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Component;
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
