package com.studentportal.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentportal.webapp.models.admin;


public interface adminRepo extends JpaRepository<admin, Long>{
    
}
