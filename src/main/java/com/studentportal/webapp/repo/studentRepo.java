package com.studentportal.webapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;



public interface studentRepo extends JpaRepository<student, Long>{
    @Query(value = "SELECT s.* FROM student s WHERE s.student_email = ?1",nativeQuery = true)
    public student getStudentByEmail(@Param("email") String email); 


    @Query(value = "SELECT c.* FROM course c INNER JOIN register r ON c.course_id = r.course_id INNER JOIN student s ON  s.student_id = r.student_id WHERE s.student_id = ?1",nativeQuery = true)
    public List<course> getStudentCourses(@Param("student_id") Long student_id); 

}
