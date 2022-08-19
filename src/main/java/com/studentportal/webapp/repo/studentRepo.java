package com.studentportal.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentportal.webapp.models.student;


public interface studentRepo extends JpaRepository<student, Long>{
    @Query(value = "SELECT s.* FROM student s WHERE s.student_email = ?1",nativeQuery = true)
    public student getStudentByEmail(@Param("email") String email); 
}
