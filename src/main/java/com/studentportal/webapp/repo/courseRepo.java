package com.studentportal.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.studentportal.webapp.models.course;



public interface courseRepo extends JpaRepository<course, Long>{
    
}
